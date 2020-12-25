package com.changgou.order.service;

import com.changgou.order.pojo.OrderItem;

import java.util.List;

public interface CartService {

    void addCart(String username, Long id, int num) throws Exception;

    List<OrderItem> getList(String username);

    void addCart(Long id, int num) throws Exception;

    List<OrderItem> getList();
}
