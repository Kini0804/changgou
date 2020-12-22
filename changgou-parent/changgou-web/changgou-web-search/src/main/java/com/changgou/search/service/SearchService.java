package com.changgou.search.service;

import com.changgou.search.pojo.SkuInfo;
import entity.Result;

import java.util.List;
import java.util.Map;

public interface SearchService {
    Result<Map<String, Object>> searchSkuInfo(Map<String, String> map);

    String getUrl(Map<String, String> map);
}
