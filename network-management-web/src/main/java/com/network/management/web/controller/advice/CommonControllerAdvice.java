package com.network.management.web.controller.advice;

import com.network.management.common.exception.IllegalParamException;
import com.network.management.web.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author yusheng
 */
@RestControllerAdvice(basePackages = {"com.network.management.web.controller"})
@Slf4j
public class CommonControllerAdvice {

    @ExceptionHandler(Exception.class)
    public Result handle(Exception ex){
        if(ex instanceof IllegalParamException){
            return Result.failure(ex.getMessage());
        }
        log.error("common error", ex);
        return Result.failure("system error");
    }
}
