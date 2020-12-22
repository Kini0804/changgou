package com.changgou.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.search.dao.SkuEsMapper;
import com.changgou.search.pojo.SkuInfo;
import com.changgou.search.service.SearchService;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.xdevapi.JsonArray;
import entity.Result;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.repository.query.RepositoryQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import springfox.documentation.spring.web.json.Json;

import java.util.*;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    SkuEsMapper skuEsMapper;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    SkuFeign skuFeign;

    @Override
    public void importDataToEs() {
        Result<List<Sku>> skuList = skuFeign.findAll();
        List<SkuInfo> skuInfoList = JSON.parseArray(JSON.toJSONString(skuList.getData()), SkuInfo.class);
        for (SkuInfo skuInfo : skuInfoList) {
            String spec = skuInfo.getSpec();
            Map<String, Object> specMap = JSON.parseObject(spec);
            skuInfo.setSpecMap(specMap);
        }
        skuEsMapper.saveAll(skuInfoList);
    }

    @Override
    public Map<String, Object> searchSkuInfo(Map searchMap) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = getNativeSearchQueryBuilder(searchMap);
        AggregatedPage<SkuInfo> skuInfos = elasticsearchTemplate.queryForPage(nativeSearchQueryBuilder.build(), SkuInfo.class, new SearchResultMapperImpl());

        List<SkuInfo> content = skuInfos.getContent();

        StringTerms stringTerms = (StringTerms) skuInfos.getAggregation("categoryName");
        List<String> categoryNameList = getKeyStringsList(stringTerms);

        StringTerms brandTerms = (StringTerms) skuInfos.getAggregation("brandName");
        List<String> brandNameList = getKeyStringsList(brandTerms);

        StringTerms specTerms = (StringTerms) skuInfos.getAggregation("specGroup");
        Map<String, Set<String>> specMap = getSpecList(specTerms);

        Map<String, Object> map = new HashMap<>();
        map.put("totalElements", skuInfos.getTotalElements());
        map.put("totalPages", skuInfos.getTotalPages());

        map.put("SkuInfoList", content);
        map.put("categoryName", categoryNameList);
        map.put("brandNameList", brandNameList);
        map.put("specMap", specMap);
        map.put("pageNum", skuInfos.getPageable().getPageNumber() + 1);
        map.put("pageSize", skuInfos.getPageable().getPageSize());

        System.out.println(map);
        return map;
    }

    private Map<String, Set<String>> getSpecList(StringTerms specTerms) {
        Map<String, Set<String>> specMap = new HashMap<>();
        if (specTerms != null){
            for (StringTerms.Bucket bucket : specTerms.getBuckets()) {
                String specString = bucket.getKeyAsString();
                Map<String, Object> map = JSON.parseObject(specString);
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    String key = entry.getKey();
                    String val = (String) entry.getValue();
                    Set<String> specSet = specMap.get(key);
                    if (specSet == null){
                        specSet = new HashSet<>();
                    }
                    specSet.add(val);
                    specMap.put(key, specSet);
                }
            }
        }
        return specMap;
    }

    private NativeSearchQueryBuilder getNativeSearchQueryBuilder(Map<String, String> searchMap) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        nativeSearchQueryBuilder.withHighlightFields(new HighlightBuilder.Field("name"));
        nativeSearchQueryBuilder.withHighlightBuilder(new HighlightBuilder().preTags("<em style=\"color:red\">").postTags("</em>"));

        if (searchMap != null && !StringUtils.isEmpty(searchMap.get("keyword"))){
            nativeSearchQueryBuilder.withQuery(QueryBuilders.multiMatchQuery(searchMap.get("keyword"), "name", "brandName", "categoryName"));
        }
        PageRequest page = PageRequest.of(0, 10);
        if (searchMap != null && !StringUtils.isEmpty(searchMap.get("page"))){
            page = PageRequest.of(Integer.parseInt(searchMap.get("page")) -1, 10);
        }
        nativeSearchQueryBuilder.withPageable(page);

        if (searchMap != null && !StringUtils.isEmpty(searchMap.get("sortRule")) && !StringUtils.isEmpty(searchMap.get("sortFiled"))){
            String sortRule = searchMap.get("sortRule");
            String sortFiled = searchMap.get("sortFiled");
            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort(sortFiled).order(sortRule.equals("DESC")? SortOrder.DESC:SortOrder.ASC));
        }

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        if (searchMap != null){
            if (!StringUtils.isEmpty(searchMap.get("categoryName"))){
                boolQueryBuilder.filter(QueryBuilders.termsQuery("categoryName", searchMap.get("categoryName")));
            }
            if (!StringUtils.isEmpty(searchMap.get("brandName"))){
                boolQueryBuilder.filter(QueryBuilders.termsQuery("brandName", searchMap.get("brandName")));
            }
            if (!StringUtils.isEmpty(searchMap.get("price"))){
                String[] prices = searchMap.get("price").split("-");
                if (prices[1].equals("*")){
                    boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(prices[0]));
                }else {
                    boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").from(prices[0]).to(prices[1]));
                }
            }
            for (String searchKey : searchMap.keySet()){
                if (StringUtils.startsWithIgnoreCase(searchKey, "specMap")){
                    boolQueryBuilder.filter(QueryBuilders.termsQuery(searchKey+".keyword", searchMap.get(searchKey)));
                }
            }
        }
        nativeSearchQueryBuilder.withFilter(boolQueryBuilder);

        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("categoryName").field("categoryName").size(50));
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("brandName").field("brandName").size(50));
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("specGroup").field("spec.keyword").size(10000));
        return nativeSearchQueryBuilder;
    }

    private List<String> getKeyStringsList(StringTerms stringTerms) {
        List<String> categoryNameList = new ArrayList<>();
        if (stringTerms != null){
            for (StringTerms.Bucket bucket : stringTerms.getBuckets()) {
               categoryNameList.add(bucket.getKeyAsString());
            }
        }
        return categoryNameList;
    }
}
