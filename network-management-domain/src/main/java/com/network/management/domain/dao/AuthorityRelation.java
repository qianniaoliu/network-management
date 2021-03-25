package com.network.management.domain.dao;

import lombok.Data;

import java.util.Date;

/**
 * 用户授权明细关系表
 * @author yyc
 * @date 2021/3/25 11:25
 */
@Data
public class AuthorityRelation {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 授权id
     */
    private Integer authorityId;

    /**
     * 用户id
     */
    private Integer userId;

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
