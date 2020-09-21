package com.network.management.domain;

import lombok.Data;

/**
 * @author yusheng
 */
@Data
public class UserSearch {
    /**
     * 用户名
     */
    private String username;

    /**
     * 分页大小
     */
    private Integer pageSize;

    /**
     * 当前页
     */
    private Integer currentPage;


    private Integer getStartIndex(){
        return (currentPage - 1) * pageSize;
    }
}
