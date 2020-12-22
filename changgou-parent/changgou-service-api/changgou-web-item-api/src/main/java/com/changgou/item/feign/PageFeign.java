package com.changgou.item.feign;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient("webItem")
@RequestMapping("/page")
public interface PageFeign {
    @GetMapping("/{id}")
    public String getPage(@PathVariable Long id);

    @RequestMapping("/createHtml/{id}")
    @ResponseBody
    public Result createHtmlPage(@PathVariable Long id);
}
