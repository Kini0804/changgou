package com.changgou.goods.controller;

import com.changgou.goods.pojo.Image;
import com.changgou.goods.service.ImageService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author:shenkunlin
 * @Description:
 * @Date 2019/6/14 0:18
 *****/
@Api(value = "ImageController")
@RestController
@RequestMapping("/image")
@CrossOrigin
public class ImageController {

    @Autowired
    private ImageService imageService;

    /***
     * Image分页条件搜索实现
     * @param image
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "Image条件分页查询",notes = "分页条件查询Image方法详情",tags = {"ImageController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    })
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false) @ApiParam(name = "Image对象",value = "传入JSON数据",required = false) Image image, @PathVariable  int page, @PathVariable  int size){
        //调用ImageService实现分页条件查询Image
        PageInfo<Image> pageInfo = imageService.findPage(image, page, size);
        return new Result(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * Image分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @ApiOperation(value = "Image分页查询",notes = "分页查询Image方法详情",tags = {"ImageController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    })
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        //调用ImageService实现分页查询Image
        PageInfo<Image> pageInfo = imageService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param image
     * @return
     */
    @ApiOperation(value = "Image条件查询",notes = "条件查询Image方法详情",tags = {"ImageController"})
    @PostMapping(value = "/search" )
    public Result<List<Image>> findList(@RequestBody(required = false) @ApiParam(name = "Image对象",value = "传入JSON数据",required = false) Image image){
        //调用ImageService实现条件查询Image
        List<Image> list = imageService.findList(image);
        return new Result<List<Image>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @ApiOperation(value = "Image根据ID删除",notes = "根据ID删除Image方法详情",tags = {"ImageController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Integer id){
        //调用ImageService实现根据主键删除
        imageService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改Image数据
     * @param image
     * @param id
     * @return
     */
    @ApiOperation(value = "Image根据ID修改",notes = "根据ID修改Image方法详情",tags = {"ImageController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @PutMapping(value="/{id}")
    public Result update(@RequestBody @ApiParam(name = "Image对象",value = "传入JSON数据",required = false) Image image,@PathVariable Integer id){
        //设置主键值
        image.setId(id);
        //调用ImageService实现修改Image
        imageService.update(image);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增Image数据
     * @param image
     * @return
     */
    @ApiOperation(value = "Image添加",notes = "添加Image方法详情",tags = {"ImageController"})
    @PostMapping
    public Result add(@RequestBody  @ApiParam(name = "Image对象",value = "传入JSON数据",required = true) Image image){
        //调用ImageService实现添加Image
        imageService.add(image);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询Image数据
     * @param id
     * @return
     */
    @ApiOperation(value = "Image根据ID查询",notes = "根据ID查询Image方法详情",tags = {"ImageController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @GetMapping("/{id}")
    public Result<Image> findById(@PathVariable Integer id){
        //调用ImageService实现根据主键查询Image
        Image image = imageService.findById(id);
        return new Result<Image>(true,StatusCode.OK,"查询成功",image);
    }

    /***
     * 查询Image全部数据
     * @return
     */
    @ApiOperation(value = "查询所有Image",notes = "查询所Image有方法详情",tags = {"ImageController"})
    @GetMapping
    public Result<List<Image>> findAll(){
        //调用ImageService实现查询所有Image
        List<Image> list = imageService.findAll();
        return new Result<List<Image>>(true, StatusCode.OK,"查询成功",list) ;
    }
}
