package com.personal.tmall.redis;

/**
 * @author YunLung Chia
 * @date 2023/6/11
 */
public interface KeyPrefix {
    long expireSeconds();

    String getPrefix();
}
