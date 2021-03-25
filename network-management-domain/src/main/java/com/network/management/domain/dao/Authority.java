package com.network.management.domain.dao;

import lombok.Data;

import java.util.Date;

/**
 * 授权明细表
 * @author yyc
 * @date 2021/3/25 11:24
 */
@Data
public class Authority {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 描述
     */
    private String desc;

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
