package com.changgou.goods.service.impl;

import com.changgou.goods.dao.ImageMapper;
import com.changgou.goods.pojo.Image;
import com.changgou.goods.service.ImageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/****
 * @Author:shenkunlin
 * @Description:Image业务层接口实现类
 * @Date 2019/6/14 0:16
 *****/
@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageMapper imageMapper;


    /**
     * Image条件+分页查询
     * @param image 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Image> findPage(Image image, int page, int size){
        //分页
        PageHelper.startPage(page,size);
        //搜索条件构建
        Example example = createExample(image);
        //执行搜索
        return new PageInfo<Image>(imageMapper.selectByExample(example));
    }

    /**
     * Image分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Image> findPage(int page, int size){
        //静态分页
        PageHelper.startPage(page,size);
        //分页查询
        return new PageInfo<Image>(imageMapper.selectAll());
    }

    /**
     * Image条件查询
     * @param image
     * @return
     */
    @Override
    public List<Image> findList(Image image){
        //构建查询条件
        Example example = createExample(image);
        //根据构建的条件查询数据
        return imageMapper.selectByExample(example);
    }


    /**
     * Image构建查询对象
     * @param image
     * @return
     */
    public Example createExample(Image image){
        Example example=new Example(Image.class);
        Example.Criteria criteria = example.createCriteria();
        if(image!=null){
            // 
            if(!StringUtils.isEmpty(image.getId())){
                    criteria.andEqualTo("id",image.getId());
            }
            // 
            if(!StringUtils.isEmpty(image.getUrl())){
                    criteria.andEqualTo("url",image.getUrl());
            }
            // 
            if(!StringUtils.isEmpty(image.getStatus())){
                    criteria.andEqualTo("status",image.getStatus());
            }
        }
        return example;
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Integer id){
        imageMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改Image
     * @param image
     */
    @Override
    public void update(Image image){
        imageMapper.updateByPrimaryKey(image);
    }

    /**
     * 增加Image
     * @param image
     */
    @Override
    public void add(Image image){
        imageMapper.insert(image);
    }

    /**
     * 根据ID查询Image
     * @param id
     * @return
     */
    @Override
    public Image findById(Integer id){
        return  imageMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询Image全部数据
     * @return
     */
    @Override
    public List<Image> findAll() {
        return imageMapper.selectAll();
    }
}
