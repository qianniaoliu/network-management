package com.network.management.web.controller.advice;

import com.network.management.common.exception.IllegalParamException;
import com.network.management.web.vo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author yusheng
 */
@RestControllerAdvice(basePackages = {"com.network.management.web.controller"})
public class CommonControllerAdvice {

    @ExceptionHandler(Exception.class)
    public Result handle(Exception ex){
        if(ex instanceof IllegalParamException){
            return Result.failure(ex.getMessage());
        }
        return Result.failure("system error");
    }
}
