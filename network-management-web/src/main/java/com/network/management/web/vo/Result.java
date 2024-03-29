package com.network.management.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yusheng
 */
@Data
@ApiModel("统一返回对象")
public class Result<T> {

    private final static Integer SUCCESS_CODE = 200;

    private final static Integer ERROR_CODE = 500;

    /**
     * 状态码
     */
    @ApiModelProperty("状态码")
    private Integer code;

    /**
     * 描述信息
     */
    @ApiModelProperty("描述信息")
    private String message;

    /**
     * 数据对象
     */
    @ApiModelProperty("数据对象")
    private T data;

    public static <T> Result<T> failure(String message) {
        Result result = new Result();
        result.setCode(ERROR_CODE);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result result = new Result();
        result.setCode(SUCCESS_CODE);
        result.setData(data);
        return result;
    }
}