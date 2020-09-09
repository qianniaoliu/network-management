package com.network.management;

import lombok.Data;

import java.util.Date;

/**
 * 设备与设备的拓扑关系
 *
 * @author yusheng
 */
@Data
public class EquipmentMapping {

    /**
     * 映射关系主键id
     */
    private Integer id;

    /**
     * 设备关联巷道图id
     */
    private Integer bordInformationId;

    /**
     * 源设备
     */
    private Integer sourceId;

    /**
     * 源ip
     */
    private String sourceIp;

    /**
     * 目标设备
     */
    private Integer targetId;

    /**
     * 目标ip
     */
    private String targetIp;

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
