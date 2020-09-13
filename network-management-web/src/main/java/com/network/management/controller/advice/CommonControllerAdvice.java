package com.network.management.controller.advice;

import com.network.management.common.exception.IllegalParamException;
import com.network.management.vo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: yusheng
 * @date: 2020/9/9 0:06
 */
@RestControllerAdvice(basePackages = {"com.network.management.controller"})
public class CommonControllerAdvice {

    @ExceptionHandler(Exception.class)
    public Result handle(Exception ex){
        if(ex instanceof IllegalParamException){
            return Result.failure(ex.getMessage());
        }
        return Result.failure("system error");
    }
}
