package com.network.management.domain.dao;

import lombok.Data;

import java.util.Date;

/**
 * 用户实体对象
 * @author yusheng
 */
@Data
public class User {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

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
