package com.changgou.search.controller;

import com.changgou.search.service.SearchService;
import com.mysql.cj.x.protobuf.Mysqlx;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/search")
@CrossOrigin
public class SearchController {
    @Autowired
    SearchService searchService;

    @RequestMapping("/import")
    public void importDataToEs(){
        searchService.importDataToEs();
    }

    @GetMapping
    public Result<Map<String, Object>> searchSkuInfo(@RequestParam(required = false) Map searchMap){
        Map<String, Object> map = searchService.searchSkuInfo(searchMap);
        return new Result<>(true, StatusCode.OK, "success", map);
    }
}
