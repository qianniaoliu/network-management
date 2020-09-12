package com.network.management.bo;

import lombok.Data;

/**
 * 设备基本信息
 * @author yyc
 * @date 2020/9/12 22:00
 */
@Data
public class DeviceBo {

    /**
     * 设备id
     */
    private Integer id;

    /**
     * 设备ip
     */
    private String ip;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 设备类型
     */
    private Integer equipmentType;
}
