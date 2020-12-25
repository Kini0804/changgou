package com.changgou.order.controller;

import com.changgou.order.pojo.OrderItem;
import com.changgou.order.service.CartService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

//    @GetMapping("/add")
//    public Result addCart(String username, Long id, int num) throws Exception {
//        cartService.addCart(username, id, num);
//        return new Result(true, StatusCode.OK, "Success");
//    }

    @GetMapping("/add")
    public Result addCart(Long id, int num) throws Exception {
        cartService.addCart(id, num);
        return new Result(true, StatusCode.OK, "Success");
    }

//    @GetMapping("/list")
//    public Result getCarts(String username) throws Exception {
//        List<OrderItem> list = cartService.getList(username);
//        return new Result(true, StatusCode.OK, "Success", list);
//    }

    @GetMapping("/list")
    public Result getCarts() throws Exception {
        List<OrderItem> list = cartService.getList();
        return new Result(true, StatusCode.OK, "Success", list);
    }
}
