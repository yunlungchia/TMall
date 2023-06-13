package com.personal.tmall.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class OrderInfo {
    private Long id;

    private Long userId;

    private Long goodsId;

    private Long deliveryAddrId;

    private String goodsName;

    private Integer goodsCount;

    private Double goodsPrice;

    private Integer goodsChannel;

    private Integer status;

    private Date createDate;

    private Date payDate;

    private String orderNumber;
}