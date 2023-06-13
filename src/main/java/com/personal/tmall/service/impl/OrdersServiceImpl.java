package com.personal.tmall.service.impl;

import com.personal.tmall.entity.OrderInfo;
import com.personal.tmall.entity.SecKillOrders;
import com.personal.tmall.entity.User;
import com.personal.tmall.mapper.OrderInfoMapper;
import com.personal.tmall.mapper.SecKillOrdersMapper;
import com.personal.tmall.redis.OrderKey;
import com.personal.tmall.redis.RedisService;
import com.personal.tmall.service.GoodsService;
import com.personal.tmall.service.OrdersService;
import com.personal.tmall.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author YunLung Chia
 * @date 2023/6/12
 */
@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    SecKillOrdersMapper secKillOrdersMapper;

    @Autowired
    OrderInfoMapper orderInfoMapper;

    @Autowired
    GoodsService goodsService;

    @Autowired
    RedisService redisService;

    @Override
    public SecKillOrders queryOrderByUserIdAndGoodsId(Long userId, Long goodsId) {
        SecKillOrders order = redisService.get(OrderKey.getOrderKey, "" + goodsId, SecKillOrders.class);
        if (order == null) {
            order = secKillOrdersMapper.selectByUserIdAndGoodsId(userId, goodsId);
        }
        return order;
    }

    @Override
    public OrderInfo queryOrderByOrderId(Long orderId) {
        return orderInfoMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public void deleteOrders() {
        orderInfoMapper.deleteAll();
        secKillOrdersMapper.deleteAll();
    }

    /**
     * 生成订单
     */
    @Override
    @Transactional
    public OrderInfo insertOrder(User user, GoodsVo goodsVo) {

        // 写入OrderInfo
        OrderInfo orderInfo = OrderInfo.builder()
                .goodsId(goodsVo.getGoodsId())
                .goodsName(goodsVo.getGoodsName())
                .userId(user.getId())
                .goodsPrice(goodsVo.getGoodsPrice())
                .goodsCount(1)
                .createDate(new Date())
                .status(0)
                .goodsChannel(1).build();
        long orderInfoRes = orderInfoMapper.insert(orderInfo);

        // 查询OrderInfo表的Id
        OrderInfo latestOrderInfo = orderInfoMapper.selectByUserIdAndGoodsId(user.getId(), goodsVo.getGoodsId());

        SecKillOrders order = SecKillOrders.builder()
                .ordersId(latestOrderInfo.getId())
                .goodsId(goodsVo.getGoodsId())
                .userId(user.getId()).build();

        int secKillOrderRes = secKillOrdersMapper.insert(order);
        if (orderInfoRes > 0 && secKillOrderRes > 0) {
            redisService.set(OrderKey.getOrderKey, "" + goodsVo.getId(), order);
            return orderInfo;
        }
        return null;
    }
}
