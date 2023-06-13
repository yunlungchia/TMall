package com.personal.tmall.controller;

import com.personal.tmall.redis.RedisService;
import com.personal.tmall.result.CodeMsg;
import com.personal.tmall.result.Result;
import com.personal.tmall.service.UserService;
import com.personal.tmall.util.ValidatorUtil;
import com.personal.tmall.vo.LoginVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @author YunLung Chia
 * @date 2023/6/12
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    /**
     * 去登录页面
     */
    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    /**
     * 用户登录
     *
     * @param loginVo  login
     * @param response response
     * @return
     */
    @RequestMapping("/do_login")
    @ResponseBody
    public Result<String> login(@Validated LoginVO loginVo, HttpServletResponse response) {

        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        if (StringUtils.isEmpty(mobile)) {
            return Result.error(CodeMsg.MOBILE_ISNULL);
        }
        if (StringUtils.isEmpty(password)) {
            return Result.error(CodeMsg.PASSWORD_ISNULL);
        }
        if (!ValidatorUtil.isMobile(mobile)) {
            return Result.error(CodeMsg.MOBILE_ERROR);
        }

        String token = userService.login(loginVo, response);


        return Result.success(token);
    }
}
