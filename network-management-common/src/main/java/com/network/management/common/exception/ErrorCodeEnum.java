package com.network.management.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 业务异常类
 * @author yyc
 * @date 2020/9/13 10:15
 */
@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {
    /**系统错误*/
    SYSTEM_ERROR(10000, "system error"),
    /**http请求异常*/
    HTTPCLIENT_ERROR(10001, "httpclient error"),
    /**ping连通性异常*/
    PING_ERROR(10002, "httpclient error");
    private final int code;
    private final String pattern;

    /**
     * 通过Code还原Enum
     *
     * @param code 代码
     * @return 枚举
     */
    public static ErrorCodeEnum codeOf(int code) {
        for (ErrorCodeEnum value : values()) {
            if (Objects.equals(value.getCode(), code)) {
                return value;
            }
        }
        return null;
    }
}
