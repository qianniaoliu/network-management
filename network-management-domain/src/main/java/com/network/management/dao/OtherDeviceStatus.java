package com.network.management.dao;

import lombok.Data;

import java.util.Date;

/**
 * 其他设备状态数据类
 * @author yyc
 * @date 2020/9/12 22:54
 */
@Data
public class OtherDeviceStatus {
    private Long id;
    /**
     * ip
     */
    private String ip;
    /**
     * 设备类型
     */
    private String type;
    /**
     * ping连接状态 {@link com.network.management.enums.YnEnum}
     */
    private Integer status;
    /**
     * 有效状态
     */
    private Integer yn;

    /**
     * 修改时间
     */
    private Date modified;

    /**
     * 创建时间
     */
    private Date created;
}
