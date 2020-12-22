package com.changgou.goods.controller;

import com.changgou.goods.pojo.Brand;
import com.changgou.goods.service.BrandService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;
    @Autowired
    private DataSource dataSource;

    Logger logger = LoggerFactory.getLogger(getClass());

    /***
     * 查询全部数据
     * @return
     */
    @GetMapping
    public Result<Brand> findAll(){
        System.out.println(dataSource.getClass());
        logger.info("asdasdasdasd");
        List<Brand> brandList = brandService.findAll();
        return new Result<Brand>(true, StatusCode.OK,"查询成功",brandList) ;
    }

    @GetMapping("/{id}")
    public Result<Brand> findById(@PathVariable Integer id){
        return new Result<Brand>(true, StatusCode.OK, "success", brandService.findById(id));
    }

    @PostMapping("/list")
    public Result<Brand> findList(@RequestBody(required = false) Brand brand){
        return new Result<Brand>(true, StatusCode.OK, "success", brandService.findList(brand));
    }

    @PostMapping
    public Result<Brand> addBrand(@RequestBody Brand brand){
        brandService.addBrand(brand);
        return new Result<Brand>(true, StatusCode.OK, "success");
    }

    @PutMapping("/{id}")
    public Result<Brand> update(@RequestBody Brand brand, @PathVariable Integer id){
        brandService.update(id, brand);
        return new Result<Brand>(true, StatusCode.OK, "xiu gai cheng gong ");
    }

    @DeleteMapping("/{id}")
    public Result<Brand> delete(@PathVariable Integer id){
        brandService.delete(id);
        return new Result<Brand>(true, StatusCode.OK, "success");
    }

    @GetMapping("/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable Integer page, @PathVariable Integer size){
        return new Result<>(true, StatusCode.OK, "success", brandService.findPage(page, size));
    }

    /***
     * 根据分类实现品牌列表查询
     * /brand/category/{id}  分类ID
     */
    @GetMapping(value = "/category/{id}")
    public Result<List<Brand>> findBrandByCategory(@PathVariable(value = "id")Integer categoryId){
        //调用Service查询品牌数据
        List<Brand> categoryList = brandService.findBrandByCategoryId(categoryId);
        return new Result<List<Brand>>(true,StatusCode.OK,"查询成功！",categoryList);
    }
}
