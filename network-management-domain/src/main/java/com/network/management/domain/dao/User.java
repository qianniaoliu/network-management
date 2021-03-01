package com.network.management.domain.dao;

import com.network.management.domain.enums.YnEnum;
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
     * 部门id
     */
    private Integer departmentId;
    /**
     * 职位id
     */
    private Integer professionId;

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

    public void initCreateInfo(){
        setYn(YnEnum.YES.getCode());
        setCreated(new Date());
        setModified(new Date());
    }

    public void initModifyInfo(){
        setModified(new Date());
    }

}
