package com.network.management.domain.dao;

import lombok.Data;

import java.util.Date;

/**
 * 机车实体
 *
 * @author yyc
 * @date 2020/10/16 11:37
 */
@Data
public class Locomotive {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 机车ip
     */
    private String ueIp;
    /**
     * 机车编号
     */
    private Integer num;
    /**
     * 描述
     */
    private String desc;

    /**
     * imsi
     */
    private String imsi;

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
