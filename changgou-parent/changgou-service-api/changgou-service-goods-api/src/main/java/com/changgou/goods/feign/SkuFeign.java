package com.changgou.goods.feign;


import com.changgou.goods.pojo.Sku;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@FeignClient(name = "goods")
@RequestMapping("/sku")
public interface SkuFeign {


    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false) @ApiParam(name = "Sku对象",value = "传入JSON数据",required = false) Sku sku, @PathVariable  int page, @PathVariable  int size);


    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size);


    @PostMapping(value = "/search" )
    public Result<List<Sku>> findList(@RequestBody(required = false) @ApiParam(name = "Sku对象",value = "传入JSON数据",required = false) Sku sku);


    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Long id);


    @PutMapping(value="/{id}")
    public Result update(@RequestBody @ApiParam(name = "Sku对象",value = "传入JSON数据",required = false) Sku sku, @PathVariable Long id);


    @PostMapping
    public Result add(@RequestBody  @ApiParam(name = "Sku对象",value = "传入JSON数据",required = true) Sku sku);



    @GetMapping("/{id}")
    public Result<Sku> findById(@PathVariable Long id);


    @GetMapping
    public Result<List<Sku>> findAll();
}
