package com.network.management.controller.advice;

import com.network.management.exception.IllegalParamException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: yusheng
 * @date: 2020/9/9 0:06
 */
@RestControllerAdvice(basePackages = {"com.network.management.controller"})
public class CommonControllerAdvice {

    @ExceptionHandler(Exception.class)
    public String handle(Exception ex){
        if(ex instanceof IllegalParamException){
            return ex.getMessage();
        }
        return "system error";
    }
}
