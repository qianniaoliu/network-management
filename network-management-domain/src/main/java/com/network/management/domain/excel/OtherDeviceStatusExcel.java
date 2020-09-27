package com.network.management.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author yusheng
 */
@Data
public class OtherDeviceStatusExcel {

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
     * ping连接状态 {@link com.network.management.domain.enums.YnEnum}
     */
    @ExcelProperty("ping连接状态")
    private Integer status;
}
