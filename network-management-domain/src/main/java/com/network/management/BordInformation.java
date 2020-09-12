package com.network.management;

import lombok.Data;

import java.util.Date;

/**
 * 巷道图基本信息
 *
 * @author yusheng
 */
@Data
public class BordInformation {

    /**
     * 巷道信息主键id
     */
    private Integer id;

    /**
     * 巷道标题
     */
    private String name;

    /**
     * 巷道图url
     */
    private String url;

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

    public BordInformation() {
    }

    public BordInformation(Integer id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }
}
