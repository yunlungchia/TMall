package com.personal.tmall.controller;

import com.personal.tmall.entity.OrderInfo;
import com.personal.tmall.entity.User;
import com.personal.tmall.result.CodeMsg;
import com.personal.tmall.result.Result;
import com.personal.tmall.service.GoodsService;
import com.personal.tmall.service.OrdersService;
import com.personal.tmall.vo.GoodsVo;
import com.personal.tmall.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author YunLung Chia
 * @date 2023/6/12
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    GoodsService goodsService;

    @Autowired
    OrdersService ordersService;

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> detail(@RequestParam("orderId") Long orderId, User user) {
        // 首先判断用户是否登录，如果未登录，则返回
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        // 查询订单OrderInfo
        OrderInfo orderInfo = ordersService.queryOrderByOrderId(orderId);
        if (orderInfo == null) {
            return Result.error(CodeMsg.ORDER_NOTFOUND);
        }

        // 查询goodsVo
        GoodsVo goods = goodsService.getGoodsByGoodsId(orderInfo.getGoodsId());

        OrderDetailVo orderDetailVo = OrderDetailVo.builder()
                .goodsVo(goods)
                .orderInfo(orderInfo).build();
        return Result.success(orderDetailVo);
    }

}
