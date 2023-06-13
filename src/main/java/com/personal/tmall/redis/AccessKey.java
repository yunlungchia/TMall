package com.personal.tmall.redis;

/**
 * @author YunLung Chia
 * @date 2023/6/12
 */
public class AccessKey extends BasePrefix {

    public static AccessKey getAccessKey = new AccessKey(500,"accessKey");

    private AccessKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static AccessKey withExpire(int expireSeconds) {
        return new AccessKey(expireSeconds, "access");
    }
}
