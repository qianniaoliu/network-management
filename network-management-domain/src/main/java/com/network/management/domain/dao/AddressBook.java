package com.network.management.domain.dao;

import lombok.Data;

import java.util.Date;

/**
 * 通讯录对象
 * @author yyc
 * @date 2021/2/27 14:02
 */
@Data
public class AddressBook {
    /**
     * id
     */
    private Integer id;
    /**
     * 通讯录名称
     */
    private String addressBookName;
    /**
     * 部门id
     */
    private Integer departmentId;
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
