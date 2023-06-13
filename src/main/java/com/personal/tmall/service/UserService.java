package com.personal.tmall.service;

import com.personal.tmall.entity.User;
import com.personal.tmall.vo.LoginVO;

import javax.servlet.http.HttpServletResponse;

/**
 * @author YunLung Chia
 * @date 2023/6/11
 */
public interface UserService {

    /**
     * 查询用户
     *
     * @param mobile mobile
     * @return user
     */
    User queryUserByMobile(long mobile);

    String login(LoginVO loginVo, HttpServletResponse response);

    /**
     * 通过token获取登录信息, 并更新缓存时间
     *
     * @param token
     * @param response
     * @return
     */
    User getUserByToken(String token, HttpServletResponse response);
}
