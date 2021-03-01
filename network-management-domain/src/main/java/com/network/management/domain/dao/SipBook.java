package com.network.management.domain.dao;

import lombok.Data;

import java.util.Date;

/**
 * 通讯录sip
 * @author yyc
 * @date 2021/2/28 10:50
 */
@Data
public class SipBook {
    /**
     * id
     */
    private Integer id;
    /**
     * 通讯录id
     */
    private Integer addressBookId;
    /**
     * sip(调度系统id)
     */
    private Integer sip;
    /**
     * sip用户名称(调度系统)
     */
    private String sipName;
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
