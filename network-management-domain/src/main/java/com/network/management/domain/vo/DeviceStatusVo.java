package com.network.management.domain.vo;

import lombok.Data;

/**
 * 前端设备信息
 * @author yyc
 * @date 2020/9/15 20:50
 */
@Data
public class DeviceStatusVo<T> {
    /**
     * 设备关联巷道图id
     */
    private Integer bordInformationId;

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

    /**
     * 数据采集间隔时间
     */
    private Long internalTime;

    /**
     * x坐标
     */
    private Integer x;

    /**
     * y坐标
     */
    private Integer y;

    /**
     * 用户名（只有基站才显示）
     */
    private String username;

    /**
     * 密码（只有基站才显示）
     */
    private String password;
    /**
     * 位置
     */
    private String position;
    /**
     * 设备状态对象
     */
    private T statusObj;
}
