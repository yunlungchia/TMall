package com.personal.tmall.controller;

import com.personal.tmall.entity.User;
import com.personal.tmall.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author YunLung Chia
 * @date 2023/6/12
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/user_info")
    @ResponseBody
    public Result<User> list(User user) {
        log.info("user info:{}", user.toString());
        return Result.success(user);
    }
}
