package com.network.management.domain.bo;

import lombok.Data;

/**
 * flash界面的基站状态数据
 * @author yyc
 * @date 2020/9/12 21:55
 */
@Data
public class FlashStationStatusBo{
    /**
     * wan口状态
     */
    private String wanStatus;
    /**
     * wan口连接状态
     */
    private String wanInternet;
    /**
     * ipsec开关状态
     */
    private String ipSecSwitch;
    /**
     * ipsec状态
     */
    private String ipSecStatus;
    /**
     * s1状态
     */
    private String s1Status;
    /**
     * 小区状态
     */
    private String cellStatus;
}
