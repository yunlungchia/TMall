package com.personal.tmall.redis;

/**
 * @author YunLung Chia
 * @date 2023/6/12
 */
public class GoodsKey extends BasePrefix{

    public static  GoodsKey getGoodsList =new GoodsKey(60,"goodsList");
    public static  GoodsKey getGoodsDetail =new GoodsKey(60,"goodsDetail");
    public static  GoodsKey getGoodsStock =new GoodsKey(0,"goodsStock");


    public GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
