package com.network.management.domain.vo;

import lombok.Data;

/**
 * web界面基站状态数据
 * @author yyc
 * @date 2020/9/15 20:49
 */
@Data
public class WebStationStatusVo{
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
}
