package com.network.management.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 状态数据表
 * @author yyc
 * @date 2020/9/29 13:49
 */
@Getter
@AllArgsConstructor
public enum StatusTableEnum {
    OTHER_DEVICE_STATUS_TABLE("other_device_status", "其他设备状态数据表"),
    STATION_STATUS_TABLE("station_status", "基站设备状态数据表");

    private String tableName;
    private String desc;
}
