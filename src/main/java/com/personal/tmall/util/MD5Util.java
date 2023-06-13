package com.personal.tmall.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author YunLung Chia
 * @date 2023/6/12
 */
public class MD5Util {

    private static final String SALT = "1a2b3c4d";

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    /**
     * md5加密
     * @param input 待加密内容
     * @param salt 加密salt
     * @return res
     */
    public static String encryptPass(String input, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + input + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    /**
     * 两次md5加密
     */
    public static String inputPassToDbPass(String inputPass, String salt) {
        // 第一次加密用于网络传输(使用自定义salt)
        String res = encryptPass(inputPass,SALT);
        // 第二次加密用于存储到数据库(使用用户存在库中的salt)
        return encryptPass(res, salt);
    }

}
