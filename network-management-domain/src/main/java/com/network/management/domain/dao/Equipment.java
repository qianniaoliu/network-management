package com.network.management.domain.dao;

import lombok.Data;

import java.util.Date;

/**
 * 设备信息
 *
 * @author yusheng
 */
@Data
public class Equipment {

    /**
     * 设备id
     */
    private Integer id;

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
    private Integer internalTime;

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
     * 有效状态
     */
    private Integer yn;

    /**
     * 修改时间
     */
    private Date modified;

    /**
     * 创建时间
     */
    private Date created;
}
