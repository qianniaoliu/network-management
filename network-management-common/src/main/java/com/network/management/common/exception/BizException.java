package com.network.management.common.exception;

/**
 * 业务异常类
 * @author yyc
 * @date 2020/9/13 10:13
 */
public class BizException extends RuntimeException{
    /**
     * 错误码枚举定义
     *
     * @see ErrorCodeEnum
     */
    private final int code;
    /**
     * 需要返回给前端的异常数据
     */
    private Object data;

    /**
     * 默认构造函数
     *
     * @param message 消息
     * @param code    错误码
     */
    public BizException(String message, int code) {
        super(message);
        this.code = code;
    }

    public BizException(String message) {
        super(message);
        this.code = ErrorCodeEnum.SYSTEM_ERROR.getCode();
    }

    public BizException(ErrorCodeEnum codeEnum) {
        super(codeEnum.getPattern());
        this.code = codeEnum.getCode();
    }
}
