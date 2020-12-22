package com.changgou.item.service.impl;

import com.alibaba.fastjson.JSON;
import com.changgou.goods.feign.CategoryFeign;
import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.feign.SpuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.goods.pojo.Spu;
import com.changgou.item.service.PageService;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class PageServiceImpl implements PageService {

    @Value("${pagepath}")
    String pagepath;


    @Autowired
    TemplateEngine templateEngine;

    @Autowired
    SkuFeign skuFeign;

    @Autowired
    SpuFeign spuFeign;

    @Autowired
    CategoryFeign categoryFeign;

    @Override
    public void createHtmlPage(Long id) {
        Context context = new Context();
        Map<String, Object> dataModel = buildDataModel(id);
        context.setVariables(dataModel);
        File dir = new File(pagepath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        System.out.println(dir.getAbsolutePath());
        File dest = new File(dir, id + ".html");
        try {
            PrintWriter writer = new PrintWriter(dest);
            templateEngine.process("item", context, writer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getOrCreateHtmlPage(Long id) {
        File dir = new File(pagepath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        System.out.println(dir.getAbsolutePath());
        File dest = new File(dir, id + ".html");
        System.out.println(dest.exists());
        if (dest.exists()){
            return;
        }
        createHtmlPage(id);
    }

    private Map<String, Object> buildDataModel(Long spuId) {
        Map<String, Object> dataModel = new HashMap<>();
        Spu spu = spuFeign.findById(spuId).getData();
        dataModel.put("spu", spu);
        dataModel.put("category1", categoryFeign.findById(spu.getCategory1Id()).getData());
        dataModel.put("category2", categoryFeign.findById(spu.getCategory2Id()).getData());
        dataModel.put("category3", categoryFeign.findById(spu.getCategory3Id()).getData());
        dataModel.put("imageList", spu.getImages().split(","));
        dataModel.put("specificationList", JSON.parseObject(spu.getSpecItems(),Map.class));
        Sku skuCondition = new Sku();
        skuCondition.setSpuId(spuId);
        List<Sku> skuList = skuFeign.findList(skuCondition).getData();
        dataModel.put("skuList", skuList);
        dataModel.put("sku", skuList.get(0));
        System.out.println(spu);
        return dataModel;
    }
}
