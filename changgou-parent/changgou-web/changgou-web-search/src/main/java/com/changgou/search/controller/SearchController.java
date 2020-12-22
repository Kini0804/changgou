package com.changgou.search.controller;

import com.changgou.search.pojo.SkuInfo;
import com.changgou.search.service.SearchService;
import entity.Page;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/search")
public class SearchController {
    @Autowired
    SearchService searchService;

    @GetMapping("/list")
    public String searchSkuInfo(@RequestParam(required = false) Map<String, String> map, Model model){
        Result<Map<String, Object>> mapResult = searchService.searchSkuInfo(map);
        System.out.println(mapResult.getData());
        Page<SkuInfo> page = new Page<SkuInfo>(
                Long.parseLong(mapResult.getData().get("totalPages").toString()),
                Integer.parseInt(mapResult.getData().get("pageNum").toString()),
                Integer.parseInt(mapResult.getData().get("pageSize").toString()));

        model.addAttribute("page", page);
        model.addAttribute("mapResult",mapResult.getData());
        model.addAttribute("searchMap", map);
        model.addAttribute("url", searchService.getUrl(map));
        System.out.println(page.toString());
        return "search";
    }
}
