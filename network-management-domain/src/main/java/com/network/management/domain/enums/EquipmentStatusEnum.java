package com.network.management.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author yusheng
 */
@Getter
@AllArgsConstructor
public enum EquipmentStatusEnum {
    /**
     * 红色
     */
    ONE(1, "红色"),

    /**
     * 黄色
     */
    TWO(2, "黄色"),

    /**
     * 绿色
     */
    THREE(3, "绿色");

    private Integer code;

    private String desc;


}
