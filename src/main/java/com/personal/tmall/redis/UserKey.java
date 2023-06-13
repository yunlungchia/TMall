package com.personal.tmall.redis;

/**
 * @author YunLung Chia
 * @date 2023/6/12
 */
public class UserKey extends BasePrefix {

    public static final int TOKEN_EXPIRE = 3600 * 24 * 2;

    public static UserKey getToken = new UserKey(TOKEN_EXPIRE, "tk");

    public UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
