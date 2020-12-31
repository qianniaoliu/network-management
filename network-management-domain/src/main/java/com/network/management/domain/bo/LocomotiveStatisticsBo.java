package com.network.management.domain.bo;

import lombok.Data;

/**
 * 机车统计数据类
 * @author yyc
 * @date 2020/12/31 19:10
 */
@Data
public class LocomotiveStatisticsBo {
    /**
     * 操作状态
     */
    private Integer status_code;
    /**
     * 错误信息
     */
    private String message;
    /**
     * 机车数量统计
     */
    private LocomotiveNumberBo data;
}
