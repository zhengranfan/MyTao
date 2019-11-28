package com.mytao.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mytao.common.pojo.MytaoResult;
import com.mytao.common.utils.ExceptionUtil;
import com.mytao.order.pojo.Order;
import com.mytao.order.service.OrderService;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/create")
    @ResponseBody
    public MytaoResult createOrder(@RequestBody Order order) {
        try {
        	MytaoResult result = orderService.createOrder(order, order.getOrderItems(), order.getOrderShipping());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return MytaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
}
