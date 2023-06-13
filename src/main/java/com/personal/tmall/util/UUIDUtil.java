package com.personal.tmall.util;

import java.util.UUID;

/**
 * @author YunLung Chia
 * @date 2023/6/12
 */
public class UUIDUtil {
    /**
     * universally unique identifier
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
