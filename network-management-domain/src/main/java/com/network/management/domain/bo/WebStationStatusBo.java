package com.network.management.domain.bo;

import lombok.Data;

/**
 * web界面的基站状态数据
 * @author yyc
 * @date 2020/9/12 21:57
 */
@Data
public class WebStationStatusBo{
    /**
     * RF状态
     */
    private String rfStatus;
    /**
     * SCTP链路状态
     */
    private String sctpStatus;
    /**
     * IPSec状态
     */
    private String ipSpecStatus;
    /**
     * 小区状态
     */
    private String cellStatus;
    /**
     * WAN口状态
     */
    private String wanStatus;
    /**
     * 网管连接状态
     */
    private String netManagerStatus;
    /**
     * 时钟状态
     */
    private String timeClockStatus;
    /**
     * AP状态
     */
    private String apStatus;
    /**
     * C820状态
     */
    private String ucStatus;
}
