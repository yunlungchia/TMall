package com.personal.tmall.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SecKillOrders {
    private Long id;

    private Long userId;

    private Long ordersId;

    private Long goodsId;
}