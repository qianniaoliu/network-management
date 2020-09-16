package com.network.management.domain.vo;

import lombok.Data;

/**
 * flash界面基站状态数据
 * @author yyc
 * @date 2020/9/15 20:49
 */
@Data
public class FlashStationStatusVo{
    /**
     * wan口状态
     */
    private Integer wanStatus;
    /**
     * wan口连接状态
     */
    private Integer wanInternet;
    /**
     * ipsec开关状态
     */
    private Integer ipSecSwitch;
    /**
     * ipsec状态
     */
    private Integer ipSecStatus;
    /**
     * s1状态
     */
    private Integer s1Status;
    /**
     * 小区状态
     */
    private Integer cellStatus;
}
