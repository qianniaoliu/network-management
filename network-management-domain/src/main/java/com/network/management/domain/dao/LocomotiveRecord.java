package com.network.management.domain.dao;

import lombok.Data;

import java.util.Date;

/**
 * @author <a href="mailto:cereb.shen@gmail.com">shenlong</a>
 */
@Data
public class LocomotiveRecord {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 车辆类型
     */
    private String vehicleType;

    /**
     * 进出方向
     */
    private String direction;

    /**
     * 节数
     */
    private Integer sectionNumber;

    /**
     * 位置（南翼/北翼）
     */
    private String location;

    /**
     * 发生时间
     */
    private Date occurDate;

    /**
     * 是否有效
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
