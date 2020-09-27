package com.network.management.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author yusheng
 */
@Data
public class FlashStationStatusExcel {

    /**
     * 设备ip
     */
    @ExcelProperty("设备ip")
    private String ip;

    /**
     * 设备名称
     */
    @ExcelProperty("设备名称")
    private String name;

    /**
     * 设备位置
     */
    @ExcelProperty("设备位置")
    private String position;

    /**
     * wan口状态
     */
    @ExcelProperty("wan口状态")
    private Integer wanStatus;
    /**
     * wan口连接状态
     */
    @ExcelProperty("wan口连接状态")
    private Integer wanInternet;
    /**
     * ipsec开关状态
     */
    @ExcelProperty("ipsec开关状态")
    private Integer ipSecSwitch;
    /**
     * ipsec状态
     */
    @ExcelProperty("ipsec状态")
    private Integer ipSecStatus;
    /**
     * s1状态
     */
    @ExcelProperty("s1状态")
    private Integer s1Status;
    /**
     * 小区状态
     */
    @ExcelProperty("小区状态")
    private Integer cellStatus;
}
