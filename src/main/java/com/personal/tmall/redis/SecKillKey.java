package com.personal.tmall.redis;

/**
 * @author YunLung Chia
 * @date 2023/6/12
 */
public class SecKillKey extends BasePrefix{

    public static SecKillKey  getSecKillPath= new SecKillKey(60,"secKillPath");
    public static SecKillKey  getSecKillVerifyCode= new SecKillKey(600,"secKillVerifyCode");

    public SecKillKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
