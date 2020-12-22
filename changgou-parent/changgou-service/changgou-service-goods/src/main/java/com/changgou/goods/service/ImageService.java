package com.changgou.goods.service;

import com.changgou.goods.pojo.Image;
import com.github.pagehelper.PageInfo;

import java.util.List;

/****
 * @Author:shenkunlin
 * @Description:Image业务层接口
 * @Date 2019/6/14 0:16
 *****/
public interface ImageService {

    /***
     * Image多条件分页查询
     * @param image
     * @param page
     * @param size
     * @return
     */
    PageInfo<Image> findPage(Image image, int page, int size);

    /***
     * Image分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<Image> findPage(int page, int size);

    /***
     * Image多条件搜索方法
     * @param image
     * @return
     */
    List<Image> findList(Image image);

    /***
     * 删除Image
     * @param id
     */
    void delete(Integer id);

    /***
     * 修改Image数据
     * @param image
     */
    void update(Image image);

    /***
     * 新增Image
     * @param image
     */
    void add(Image image);

    /**
     * 根据ID查询Image
     * @param id
     * @return
     */
     Image findById(Integer id);

    /***
     * 查询所有Image
     * @return
     */
    List<Image> findAll();
}
