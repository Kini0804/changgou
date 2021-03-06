package com.changgou.goods.service;

import com.changgou.goods.pojo.Brand;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BrandService {
    List<Brand> findAll();

    Brand findById(Integer id);

    void addBrand(Brand brand);

    void update(Integer id, Brand brand);

    void delete(Integer id);

    List<Brand> findList(Brand brand);

    PageInfo<Brand> findPage(Integer page, Integer size);

    List<Brand> findBrandByCategoryId(Integer categoryId);
}
