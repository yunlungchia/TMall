package com.personal.tmall.mq;

import com.personal.tmall.biz.SecKillBiz;
import com.personal.tmall.entity.OrderInfo;
import com.personal.tmall.entity.SecKillOrders;
import com.personal.tmall.redis.RedisService;
import com.personal.tmall.service.GoodsService;
import com.personal.tmall.service.OrdersService;
import com.personal.tmall.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author YunLung Chia
 * @date 2023/6/12
 */
@Slf4j
@Service
public class MQConsumer {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrdersService ordersService;

    @Autowired
    SecKillBiz secKillBiz;

    @RabbitListener(queues = {"${tmall.rabbitmq.topic.queue}"})
    public void consume(String message) {
        log.info("Receive Message:{}", message);
        UserAndGoodsMsg msg = RedisService.stringToBean(message, UserAndGoodsMsg.class);

        // 判断是否有库存
        GoodsVo goodsVo = goodsService.getGoodsByGoodsId(msg.getGoodsId());
        if(goodsVo.getStockCount() <= 0){
            log.info("SecKill Fail, 秒杀商品无库存");
            return;
        }

        // 判断重复秒杀
        SecKillOrders secKillOrder = ordersService.queryOrderByUserIdAndGoodsId(msg.getUser().getId(), msg.getGoodsId());
        if(secKillOrder != null) {
            log.info("SecKill Fail, 该用户存在秒杀订单");
            return;
        }

        // 减库存 下订单 写入秒杀订单
        OrderInfo orderInfo = secKillBiz.secKill(msg.getUser(), goodsVo);


        log.info("SecKill Success, OrderInfo:{}", orderInfo.toString());
    }

}
