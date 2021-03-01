package com.network.management.domain.dao;

import lombok.Data;

import java.util.Date;

/**
 * 职业对象
 * @author yyc
 * @date 2021/2/27 13:21
 */
@Data
public class Profession {
    /**
     * 职位id
     */
    private Integer id;
    /**
     * 职位名称
     */
    private String professionName;

    /**
     * 职位等级
     */
    private Integer level;
    /**
     * 权限等级
     */
    private Integer priority;

    /**
     * 职位描述
     */
    private String description;

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
