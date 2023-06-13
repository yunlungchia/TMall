package com.personal.tmall.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author YunLung Chia
 * @date 2023/6/11
 */
@Getter
@AllArgsConstructor
public class CodeMsg {

    private int code;
    private String msg;

    /**
     * 通用异常
     */
    public static CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static CodeMsg SESSION_ERROR = new CodeMsg(500101, "登录错误");
    public static CodeMsg REQUEST_ILLEGAL = new CodeMsg(500102, "请求非法");
    public static CodeMsg BIND_ERROR = new CodeMsg(500103, "参数校验异常");

    /**
     * 登录模块 5002XX
     */
    public static CodeMsg MOBILE_ISNULL = new CodeMsg(500200, "输入手机号为空");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500201, "手机号码格式错误");
    public static CodeMsg PASSWORD_ISNULL = new CodeMsg(500202, "密码为空");
    public static CodeMsg NOTFOUND_MOBILE = new CodeMsg(500203, "没有查到该手机号");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500204, "密码输入错误");
    public static CodeMsg LOGIN_TIMEOUT = new CodeMsg(500205, "登录超时");

    /**
     * 商品模块 5003XX
     */
    public static CodeMsg NO_GOODS = new CodeMsg(500301, "已经没有库存");
    public static CodeMsg NO_REPEAT_BUY = new CodeMsg(500302, "不能重复购买");


    /**
     * 订单模块 5004XX
     */
    public static CodeMsg ORDER_NOTFOUND= new CodeMsg(500401, "订单找不到");


    /**
     * 秒杀模块 5005XX
     */
    public static CodeMsg SECKILL_STOCKISNULL= new CodeMsg(500501, "库存已空");
    public static CodeMsg SECKILL_ISREPEATED= new CodeMsg(500502, "已经秒杀过");
    public static CodeMsg SECKILL_FAIL = new CodeMsg(500103, "秒杀失败");
    public static CodeMsg ACCESS_LIMIT_REACHED = new CodeMsg(500104, "访问达到上限");
}
