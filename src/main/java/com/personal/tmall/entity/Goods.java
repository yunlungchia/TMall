package com.personal.tmall.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Goods {
    private Long id;

    private String goodsName;

    private String goodsTitle;

    private String goodsImg;

    private Double goodsPrice;

    private Integer goodsStock;

    private String goodsDetail;
}