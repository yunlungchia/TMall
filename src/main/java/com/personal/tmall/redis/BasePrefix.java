package com.personal.tmall.redis;

/**
 * @author YunLung Chia
 * @date 2023/6/12
 */
public abstract class BasePrefix implements KeyPrefix {

    private long expireSeconds;

    private String prefix;

    public BasePrefix(String prefix) {
        // 0代表永不过期
        this(0, prefix);
    }

    public BasePrefix(long expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    public long expireSeconds() {
        return expireSeconds;
    }

    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }

}
