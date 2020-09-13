package com.network.management.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * web界面基站状态数据key枚举
 * @author yyc
 * @date 2020/9/13 11:35
 */
@AllArgsConstructor
@Getter
public enum WebStationKeyEnum {
    RF_STATUS("C325_RFTxStatus", "RF状态"),
    SCTP_STATUS("C321_Status", "SCTP链路状态"),
    IP_SPEC_STATUS("C600_Status", "IPSec状态"),
    CELL_STATUS("C320_Status", "小区状态"),
    WAN_STATUS("C800_Status", "WAN口状态"),
    MANAGEMENT_STATUS("C200_X_VENDOR_hmsConnectStatus", "网管连接状态"),
    TIME_CLOCK_STATUS("C100_TimeClockStatus", "时钟状态"),
    AP_STATUS("C321_APStatus", "AP状态"),
    C820_STATUS("C820_ucPrimSrc", "C820状态");

    private String key;
    private String desc;
}
