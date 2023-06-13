package com.personal.tmall.service.impl;

import com.personal.tmall.entity.User;
import com.personal.tmall.exception.GlobalException;
import com.personal.tmall.mapper.UserMapper;
import com.personal.tmall.redis.RedisService;
import com.personal.tmall.redis.UserKey;
import com.personal.tmall.result.CodeMsg;
import com.personal.tmall.service.UserService;
import com.personal.tmall.util.MD5Util;
import com.personal.tmall.util.UUIDUtil;
import com.personal.tmall.vo.LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author YunLung Chia
 * @date 2023/6/11
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public User queryUserByMobile(long mobile) {
        User user = redisService.get(UserKey.getToken, "" + mobile, User.class);
        if (user == null) {
            user = userMapper.selectByPrimaryKey(mobile);
            redisService.set(UserKey.getToken, "+mobile", user);
        }
        return user;
    }

    @Override
    public String login(LoginVO loginVo, HttpServletResponse response) {
        User user = queryUserByMobile(Long.parseLong(loginVo.getMobile()));

        // 登录用户不存在
        if (user == null) {
            throw new GlobalException(CodeMsg.NOTFOUND_MOBILE);
        }

        String dbPassword = user.getPassword();
        String formPassword = loginVo.getPassword();
        String formToDbPwd = MD5Util.inputPassToDbPass(formPassword, user.getSalt());

        // 登录密码错误
        if (!dbPassword.equals(formToDbPwd)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }

        // 保存登录信息
        String token = UUIDUtil.uuid();
        addOrUpdateCookie(response, token, user);
        return token;
    }

    /**
     * 添加或更新cookie
     *
     * @param response response
     * @param token    token
     * @param user     user
     */
    private void addOrUpdateCookie(HttpServletResponse response, String token, User user) {
        // First 保存一个口令到cookie
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(UserKey.TOKEN_EXPIRE);
        cookie.setPath("/");
        response.addCookie(cookie);

        // Second 把这个口令作为key，User对象作为value值保存到redis里面
        redisService.set(UserKey.getToken, token, user);
    }

    @Override
    public User getUserByToken(String token, HttpServletResponse response) {
        User user = redisService.get(UserKey.getToken, token, User.class);
        addOrUpdateCookie(response, token, user);
        return user;
    }

}
