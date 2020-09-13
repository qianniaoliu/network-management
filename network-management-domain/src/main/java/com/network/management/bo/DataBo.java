package com.network.management.bo;

import lombok.Data;

/**
 * 基础状态数据
 * @author yyc
 * @date 2020/9/12 21:51
 */
@Data
public class DataBo<T> {

    private T dataObj;
    /**
     * 设备ip
     */
    private String ip;
    /**
     * 设备类型
     */
    private Integer type;
}
