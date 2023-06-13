package com.personal.tmall.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class SecKillGoods {
    private Long id;

    private Long goodsId;

    private Double seckillPrice;

    private Integer stockCount;

    private Date startDate;

    private Date endDate;
}