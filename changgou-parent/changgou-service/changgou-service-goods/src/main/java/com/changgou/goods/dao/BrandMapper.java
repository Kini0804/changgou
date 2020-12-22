package com.changgou.goods.dao;

import com.changgou.goods.pojo.Brand;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface BrandMapper extends Mapper<Brand> {

    @Select("select tbb.* from tb_brand tbb, tb_category_brand tbc where tbc.category_id = #{categoryId} and tbb.id=tbc.brand_id")
    public List<Brand> findBrandByCategoryId(Integer categoryId);
}
