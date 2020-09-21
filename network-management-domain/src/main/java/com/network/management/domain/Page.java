package com.network.management.domain;

import lombok.Data;

import java.util.List;

/**
 * 分页对象
 *
 * @author yusheng
 */
@Data
public class Page<T> {
    /**
     * 分页数据
     */
    private List<T> data;

    /**
     * 分页大小
     */
    private Integer pageSize;

    /**
     * 当前页码
     */
    private Integer currentPage;

    /**
     * 总条数
     */
    private Integer count;
}
