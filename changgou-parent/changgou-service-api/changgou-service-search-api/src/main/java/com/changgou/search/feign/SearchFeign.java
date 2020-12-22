package com.changgou.search.feign;

import entity.Result;
import entity.StatusCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "search")
@RequestMapping("/search")
public interface SearchFeign {
    @GetMapping
    Result<Map<String, Object>> searchSkuInfo(@RequestParam(required = false) Map searchMap);
}
