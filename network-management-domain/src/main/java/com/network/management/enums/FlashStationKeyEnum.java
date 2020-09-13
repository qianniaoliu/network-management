package com.network.management.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * flash界面基站状态数据key枚举
 * @author yyc
 * @date 2020/9/13 11:29
 */
@AllArgsConstructor
@Getter
public enum FlashStationKeyEnum {
    WAN_STATUS("wanstatus", "WAN口状态"),
    WAN_INTERNET("waninternet", "网管连接状态"),
    IP_SPEC_SWITCH("ipsecswitch", "IPSec开关"),
    IP_SPEC_STATUS("ipsecstatus", "IPSec状态"),
    S1_STATUS("s1status", "S1状态"),
    CELL_STATUS("cellstatus", "小区状态");

    private String key;
    private String desc;
}
