package com.personal.tmall.service;

import com.personal.tmall.entity.OrderInfo;
import com.personal.tmall.entity.SecKillOrders;
import com.personal.tmall.entity.User;
import com.personal.tmall.vo.GoodsVo;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author YunLung Chia
 * @date 2023/6/12
 */
public interface OrdersService {
    SecKillOrders queryOrderByUserIdAndGoodsId(Long userId, Long goodsId);

    OrderInfo queryOrderByOrderId(Long orderId);

    void deleteOrders();

    @Transactional
    OrderInfo insertOrder(User user, GoodsVo goodsVo);
}
