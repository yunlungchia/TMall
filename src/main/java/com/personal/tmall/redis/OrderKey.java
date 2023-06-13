package com.personal.tmall.redis;

/**
 * @author YunLung Chia
 * @date 2023/6/12
 */
public class OrderKey extends BasePrefix{

    public static OrderKey getOrderKey = new OrderKey(0,"orderKey");

    public static OrderKey getSecKillFailKey = new OrderKey(0,"seckillfailKey");

    public OrderKey(long expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
