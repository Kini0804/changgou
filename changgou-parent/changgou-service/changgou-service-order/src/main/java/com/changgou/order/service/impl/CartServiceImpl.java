package com.changgou.order.service.impl;

import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.feign.SpuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.goods.pojo.Spu;
import com.changgou.order.pojo.OrderItem;
import com.changgou.order.service.CartService;
import entity.TokenDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    SkuFeign skuFeign;
    @Autowired
    SpuFeign spuFeign;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    TokenDecode tokenDecode;

    @Override
    public void addCart(Long id, int num) throws Exception {
        Map<String, String> userInfo = tokenDecode.getUserInfo();
        System.out.println(userInfo);
        addCart(userInfo.get("username"), id, num);
    }



    @Override
    public void addCart(String username, Long id, int num) throws Exception {
//        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        OrderItem exOrder = (OrderItem) redisTemplate.boundHashOps(username).get(id);
        if(exOrder != null){
            int currentNum = exOrder.getNum() + num;
            if(currentNum <= 0){
                redisTemplate.boundHashOps(username).delete(id);
                return;
            }
            exOrder.setNum(currentNum);
            exOrder.setMoney(currentNum*exOrder.getPrice());       //单价*数量
            exOrder.setPayMoney(currentNum*exOrder.getPrice());    //实付金额
            redisTemplate.boundHashOps(username).put(id, exOrder);
            OrderItem temp = (OrderItem) redisTemplate.boundHashOps(username).get(id);
            System.out.println(temp.getName());
            System.out.println(temp.getNum());
            return;
        }
        if(num <= 0){
            redisTemplate.boundHashOps(username).delete(id);
            return;
        }
        Sku sku = skuFeign.findById(id).getData();
        if(sku == null){
            throw new Exception("sku false");
        }
        Spu spu = spuFeign.findById(sku.getSpuId()).getData();
        OrderItem orderItem = convert(sku, spu, num);
        redisTemplate.boundHashOps(username).put(id, orderItem);
        OrderItem temp = (OrderItem) redisTemplate.boundHashOps(username).get(id);
        System.out.println(temp.getName());
    }

    @Override
    public List<OrderItem> getList() {
        Map<String, String> userInfo = tokenDecode.getUserInfo();
        System.out.println(userInfo);
        return getList(userInfo.get("username"));
    }

    @Override
    public List<OrderItem> getList(String username) {
        return redisTemplate.boundHashOps(username).values();
    }



    private OrderItem convert(Sku sku, Spu spu, Integer num) {
        OrderItem orderItem = new OrderItem();
        orderItem.setCategoryId1(spu.getCategory1Id());
        orderItem.setCategoryId2(spu.getCategory2Id());
        orderItem.setCategoryId3(spu.getCategory3Id());
        orderItem.setSkuId(sku.getId());
        orderItem.setSpuId(spu.getId());
        orderItem.setName(sku.getName());
        orderItem.setPrice(sku.getPrice());
        orderItem.setNum(num);
        orderItem.setMoney(num*orderItem.getPrice());       //单价*数量
        orderItem.setPayMoney(num*orderItem.getPrice());    //实付金额
        orderItem.setImage(sku.getImage());
        orderItem.setWeight(sku.getWeight()*num);
        return orderItem;
    }
}
