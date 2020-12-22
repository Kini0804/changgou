package com.changgou.search.service;

import com.changgou.search.dao.SkuEsMapper;
import com.changgou.search.pojo.SkuInfo;
import entity.Result;

import java.util.List;
import java.util.Map;

public interface SearchService {
    void importDataToEs();
    Map<String, Object> searchSkuInfo(Map searchMap);
}
