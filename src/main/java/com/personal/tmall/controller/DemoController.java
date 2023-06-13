package com.personal.tmall.controller;

import com.personal.tmall.mq.MQProducer;
import com.personal.tmall.redis.RedisService;
import com.personal.tmall.result.CodeMsg;
import com.personal.tmall.result.Result;
import com.personal.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author YunLung Chia
 * @date 2023/6/11
 */
@Controller
public class DemoController {

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    MQProducer mqProducer;

    @RequestMapping("/")
    public String home() {
        return "Hello World!";
    }

    /**
     * 1.rest api json输出
     * 2.页面
     */
    @RequestMapping("/helloGood")
    @ResponseBody
    public Result<String> hello() {
        return Result.success("hello,java study");
    }

    @RequestMapping("/helloError")
    @ResponseBody
    public Result<String> helloError() {
        return Result.error(CodeMsg.SERVER_ERROR);
    }

    /**
     * thymeleaf
     *
     * @param model model
     * @return String
     */
    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model) {
        model.addAttribute("name", "test");
        return "hello";
    }

    @RequestMapping("/users")
    @ResponseBody
    public String getUser() {
        return userService.queryUserByMobile(13000000000L).getNickname();
    }

    @RequestMapping("/mq/direct")
    @ResponseBody
    public Result<String> topic() {
        mqProducer.produceDirectMsg("test");
        return Result.success("Hello，world");
    }

}
