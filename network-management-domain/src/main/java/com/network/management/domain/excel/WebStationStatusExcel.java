package com.network.management.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

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
    private String rfStatus;
    /**
     * SCTP链路状态
     */
    @ExcelProperty("SCTP链路状态")
    private String sctpStatus;
    /**
     * 小区状态
     */
    @ExcelProperty("小区状态")
    private String cellStatus;
    /**
     * WAN口状态
     */
    @ExcelProperty("WAN口状态")
    private String wanStatus;
    /**
     * 网管连接状态
     */
    @ExcelProperty("网管连接状态")
    private String netManagerStatus;
    /**
     * 时钟状态
     */
    @ExcelProperty("时钟状态")
    private String timeClockStatus;
    /**
     * AP状态
     */
    @ExcelProperty("AP状态")
    private String apStatus;

    /**
     * 创建时间
     */
    @ExcelProperty("创建时间")
    private Date created;
}
