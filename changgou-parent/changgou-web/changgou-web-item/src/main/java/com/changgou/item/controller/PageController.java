package com.changgou.item.controller;

import com.changgou.item.service.PageService;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;

@Controller
@RequestMapping("/page")
public class PageController {

    @Autowired
    PageService pageService;

    @GetMapping("/{id}")
    public String getPage(@PathVariable Long id){
        pageService.getOrCreateHtmlPage(id);
        return "items/" + id;
    }

    @RequestMapping("/createHtml/{id}")
    @ResponseBody
    public Result createHtmlPage(@PathVariable Long id){
        pageService.createHtmlPage(id);
        return new Result();
    }
}
