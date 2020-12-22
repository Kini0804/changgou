package com.changgou.goods.service;

import com.changgou.goods.pojo.Album;
import com.github.pagehelper.PageInfo;
import org.csource.common.MyException;
import org.json.JSONException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AlbumService {
    List<Album> findAll();
    Album findById(Long id);
    Album add(Album album);
    Album update(Long id, Album album);
    void delete(Long id);
    List<Album> findList(Album album);
    PageInfo<Album> findPage(Integer page, Integer size);

    Album updatePic(Long id, MultipartFile multipartFile) throws JSONException, IOException, MyException;
}
