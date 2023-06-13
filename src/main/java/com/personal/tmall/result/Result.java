package com.personal.tmall.result;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author YunLung Chia
 * @date 2023/6/11
 * @desc
 */
@Data
@AllArgsConstructor
public class Result<T> {

    private int code;
    private String msg;
    private T data;

    /**
     * 成功时候的调用
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>(data);
    }

    /**
     * 失败时候的调用
     */
    public static <T> Result<T> error(CodeMsg msg) {
        return new Result<T>(msg);
    }

    private Result(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    private Result(CodeMsg msg) {
        if (msg == null) {
            return;
        }
        this.code = msg.getCode();
        this.msg = msg.getMsg();
    }
}
