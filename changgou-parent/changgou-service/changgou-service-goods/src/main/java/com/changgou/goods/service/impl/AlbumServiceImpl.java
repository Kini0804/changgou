package com.changgou.goods.service.impl;

import com.changgou.file.feign.FileRemote;
import com.changgou.goods.dao.AlbumMapper;
import com.changgou.goods.dao.ImageMapper;
import com.changgou.goods.pojo.Album;
import com.changgou.goods.pojo.Image;
import com.changgou.goods.service.AlbumService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.csource.common.MyException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumMapper albumMapper;

    @Autowired
    private FileRemote fileRemote;

    @Autowired
    private ImageMapper imageMapper;

    @Override
    public List<Album> findAll() {
//        System.out.println(fileRemote.testGet());
        return albumMapper.selectAll();
    }

    @Override
    public Album findById(Long id) {
        return albumMapper.selectByPrimaryKey(id);
    }

    @Override
    public Album add(Album album) {
        albumMapper.insert(album);
        return album;
    }

    @Override
    public Album update(Long id, Album album) {
        album.setId(id);
        albumMapper.updateByPrimaryKey(album);
        return album;
    }

    @Override
    public void delete(Long id) {
        albumMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Album> findList(Album album) {
        Example example = new Example(Album.class);
        Example.Criteria criteria = example.createCriteria();
        if (album != null){
            if (album.getId() != null){
                criteria.andEqualTo("id", album.getId());
            }
            if (album.getTitle() != null){
                criteria.andLike("title", album.getTitle());
            }
        }
        return albumMapper.selectByExample(example);
    }

    @Override
    public PageInfo<Album> findPage(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        return new PageInfo<Album>(albumMapper.selectAll());
    }

    @Override
    public Album updatePic(Long id, MultipartFile multipartFile) throws JSONException, IOException, MyException {

        String imageUrl = fileRemote.updateFile(multipartFile);
        Image image = new Image();
        image.setUrl(imageUrl);
        image.setStatus("success");
        imageMapper.insert(image);
        Album album = albumMapper.selectByPrimaryKey(id);
        JSONArray jsonArray;
        if (album.getImageItems() != null){
            jsonArray = new JSONArray(album.getImageItems());
        }else {
            jsonArray = new JSONArray();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("url", image.getUrl());
        jsonObject.put("uid", image.getId());
        jsonObject.put("status", image.getStatus());
        jsonArray.put(jsonObject);
        album.setImageItems(jsonArray.toString());
        albumMapper.updateByPrimaryKey(album);
        return album;
    }
}
