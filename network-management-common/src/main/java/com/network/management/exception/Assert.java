package com.network.management.exception;

import org.springframework.lang.Nullable;

/**
 * 断言工具类
 *
 * @author yusheng
 */
public class Assert {

    /**
     * 目标对象不能为空，为空则抛出异常
     * @param object
     * @param message
     */
    public static void notNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new IllegalParamException(message);
        }
    }

    /**
     * 目标对象必须为空，不为空则抛出异常
     * @param object
     * @param message
     */
    public static void isNull(@Nullable Object object, String message) {
        if (object != null) {
            throw new IllegalParamException(message);
        }
    }
}
