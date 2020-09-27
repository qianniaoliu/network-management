package com.network.management.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author yusheng
 */
@Data
public class WebStationStatusExcel {

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
     * RF状态
     */
    @ExcelProperty("RF状态")
    private Integer rfStatus;
    /**
     * SCTP链路状态
     */
    @ExcelProperty("SCTP链路状态")
    private Integer sctpStatus;
    /**
     * IPSec状态
     */
    @ExcelProperty("IPSec状态")
    private Integer ipSecStatus;
    /**
     * 小区状态
     */
    @ExcelProperty("小区状态")
    private Integer cellStatus;
    /**
     * WAN口状态
     */
    @ExcelProperty("WAN口状态")
    private Integer wanStatus;
    /**
     * 网管连接状态
     */
    @ExcelProperty("网管连接状态")
    private Integer netManagerStatus;
    /**
     * 时钟状态
     */
    @ExcelProperty("时钟状态")
    private Integer timeClockStatus;
    /**
     * AP状态
     */
    @ExcelProperty("AP状态")
    private Integer apStatus;
    /**
     * C820状态
     */
    @ExcelProperty("C820状态")
    private Integer ucStatus;
}
