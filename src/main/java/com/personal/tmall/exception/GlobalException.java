package com.personal.tmall.exception;


import com.personal.tmall.result.CodeMsg;

/**
 * @author YunLung Chia
 * @date 2023/6/12
 */
public class GlobalException extends RuntimeException {

    private CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg) {
        super(codeMsg.toString());
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }
}
