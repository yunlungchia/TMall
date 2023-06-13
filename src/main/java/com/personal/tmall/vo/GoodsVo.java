package com.personal.tmall.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author YunLung Chia
 * @date 2023/6/12
 */
@Data
public class GoodsVo {

    // 商品id
    private Long id;
    private String goodsName;
    private String goodsTitle;
    private String goodsImg;
    private String goodsDetail;
    private Double goodsPrice;
    private Integer goodsStock;

    // 商品id
    private Long goodsId;
    private Integer stockCount;
    private Double seckillPrice;
    private Date startDate;
    private Date endDate;
}
