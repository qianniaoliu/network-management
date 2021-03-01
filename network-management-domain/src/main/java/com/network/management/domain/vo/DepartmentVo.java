package com.network.management.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 部门对象
 * @author yyc
 * @date 2021/2/27 13:25
 */
@Data
@ApiModel("部门信息")
public class DepartmentVo {
    /**
     * 部门id
     */
    @ApiModelProperty("部门id")
    private Integer id;
    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    private String departmentName;
    /**
     * 父部门id
     */
    @ApiModelProperty("父部门id")
    private Integer parentId;
}
