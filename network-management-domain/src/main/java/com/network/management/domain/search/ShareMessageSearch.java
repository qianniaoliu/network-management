package com.network.management.domain.search;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yusheng
 */
@Data
@ApiModel("共享消息搜索对象")
public class ShareMessageSearch {

    /**
     * 分页大小
     */
    @ApiModelProperty("分页大小")
    private Integer pageSize;

    /**
     * 当前页
     */
    @ApiModelProperty("当前页")
    private Integer currentPage;


    private Integer getStartIndex(){
        return (currentPage - 1) * pageSize;
    }
}
