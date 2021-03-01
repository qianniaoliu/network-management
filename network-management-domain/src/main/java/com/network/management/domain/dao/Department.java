package com.network.management.domain.dao;

import lombok.Data;

import java.util.Date;

/**
 * 部门对象
 * @author yyc
 * @date 2021/2/27 13:25
 */
@Data
public class Department {
    /**
     * 部门id
     */
    private Integer id;
    /**
     * 部门名称
     */
    private String departmentName;
    /**
     * 父部门id
     */
    private Integer parentId;

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
