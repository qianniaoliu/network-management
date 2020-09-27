package com.network.management.domain.search;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 分页对象
 *
 * @author yusheng
 */
@Data
@ApiModel("分页返回对象")
public class Page<T> {
    /**
     * 分页数据
     */
    @ApiModelProperty("分页数据")
    private List<T> data;

    /**
     * 分页大小
     */
    @ApiModelProperty("分页大小")
    private Integer pageSize;

    /**
     * 当前页码
     */
    @ApiModelProperty("当前页码")
    private Integer currentPage;

    /**
     * 总条数
     */
    @ApiModelProperty("总条数")
    private Integer count;
}
