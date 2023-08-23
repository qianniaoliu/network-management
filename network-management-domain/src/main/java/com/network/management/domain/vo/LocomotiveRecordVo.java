package com.network.management.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * 机车记录请求对象
 *
 * @author <a href="mailto:cereb.shen@gmail.com">shenlong</a>
 */
@Data
public class LocomotiveRecordVo {

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

    public void checkParam(){

    }

}
