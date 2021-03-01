package com.network.management.domain.search;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 职位搜索对象
 * @author yyc
 * @date 2021/2/27 13:25
 */
@Data
@ApiModel("职位搜索对象")
public class ProfessionSearch {
    /**
     * 用户名
     */
    @ApiModelProperty("职位名称")
    private String professionName;

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
