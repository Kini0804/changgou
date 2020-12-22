package com.changgou.goods.controller;

import com.changgou.goods.pojo.Album;
import com.changgou.goods.service.AlbumService;
import entity.Result;
import entity.StatusCode;
import org.csource.common.MyException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @GetMapping
    public Result<Album> findAll(){
        List<Album> albumList = albumService.findAll();
        return new Result<Album>(true, StatusCode.OK, "查询成功", albumList);
    }

    @GetMapping("/{id}")
    public Result<Album> findById(@PathVariable("id")Long id){
        return new Result<Album>(true, StatusCode.OK, "查询成功", albumService.findById(id));
    }

    @PostMapping("/list")
    public Result<Album> findList(@RequestBody Album album){
        return new Result<Album>(true, StatusCode.OK, "查询成功", albumService.findList(album));
    }

    @GetMapping("/{page}/{size}")
    public Result<Album> getAlbumPage(@PathVariable("page")Integer page, @PathVariable("size") Integer size){
        return new Result<Album>(true, StatusCode.OK, "查询成功", albumService.findPage(page, size));
    }

    @PostMapping
    public Result<Album> addAlbum(@RequestBody Album album){
        return new Result<Album>(true, StatusCode.OK, "添加成功", albumService.add(album));
    }

    @DeleteMapping("/{id}")
    public Result deleteAlbum(@PathVariable("id") Long id){
        albumService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @PostMapping("/{id}")
    public Result<Album> updateAlbumPic(@PathVariable Long id, @RequestParam("file") MultipartFile multipartFile) throws JSONException, IOException, MyException {
        return new Result<Album>(true, StatusCode.OK, "增加图片成功", albumService.updatePic(id, multipartFile));
    }

    @PutMapping("/{id}")
    public Result<Album> updateAlbum(@PathVariable Long id, @RequestBody Album album){
        return new Result<Album>(true, StatusCode.OK, "修改成功", albumService.update(id, album));
    }


}
