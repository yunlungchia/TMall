package com.personal.tmall.interceptor;

import com.personal.tmall.entity.User;

/**
 * @author YunLung Chia
 * @date 2023/6/12
 */
public class UserContext {

    private static ThreadLocal<User> userHolder = new ThreadLocal<User>();

    public static void setUser(User user) {
        userHolder.set(user);
    }

    public static User getUser() {
        return userHolder.get();
    }

}
