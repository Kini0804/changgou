package com.changgou.search.service.impl;

import com.changgou.search.feign.SearchFeign;
import com.changgou.search.pojo.SkuInfo;
import com.changgou.search.service.SearchService;
import com.sun.jndi.toolkit.dir.SearchFilter;
import entity.Result;
import org.assertj.core.data.MapEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    SearchFeign searchFeign;

    @Override
    public Result<Map<String, Object>> searchSkuInfo(Map<String, String> map) {
        return searchFeign.searchSkuInfo(map);
    }

    @Override
    public String getUrl(Map<String, String> map) {
        String url = "/search/list";
        if (map != null){
            url += "?";
            for (Map.Entry<String, String> mapEntry : map.entrySet()){
                if (mapEntry.getKey().equals("page")){
                    continue;
                }
                url += mapEntry.getKey() + "=" + mapEntry.getValue() + "&";
            }
        }
        return url.substring(0, url.length() -1);
    }
}
