package com.network.management.dao;

import lombok.Data;

import java.util.Date;

/**
 * web界面的基站状态数据
 * @author yyc
 * @date 2020/9/12 21:57
 */
@Data
public class StationStatus {
    private Long id;
    /**
     * ip
     */
    private String ip;
    /**
     * 设备类型
     */
    private String type;
    /**
     * RF状态
     */
    private Integer rfStatus;
    /**
     * SCTP链路状态
     */
    private Integer sctpStatus;
    /**
     * IPSec状态
     */
    private Integer ipSpecStatus;
    /**
     * 小区状态
     */
    private Integer cellStatus;
    /**
     * WAN口状态
     */
    private Integer wanStatus;
    /**
     * 网管连接状态
     */
    private Integer netManagerStatus;
    /**
     * 时钟状态
     */
    private Integer timeClockStatus;
    /**
     * AP状态
     */
    private Integer apStatus;
    /**
     * C820状态
     */
    private Integer ucStatus;

    /**
     * s1状态
     */
    private Integer s1Status;
    /**
     * wan口连接状态
     */
    private Integer wanInternet;
    /**
     * ipsec开关状态
     */
    private Integer ipSecSwitchStatus;
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
