package com.network.management.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 电源状态枚举类
 * @author yyc
 * @date 2021/12/19 17:12
 */
@AllArgsConstructor
@Getter
public enum PowerStatusEnum {
    AC(0, "交流"),
    DC(1, "直流");

    /**
     * 电源状态
     */
    private Integer status;
    private String desc;
}
