package com.network.management.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 职业对象
 * @author yyc
 * @date 2021/2/27 13:21
 */
@Data
@ApiModel("职位信息")
public class ProfessionVo {
    /**
     * 职位id
     */
    @ApiModelProperty("职位id")
    private Integer id;
    /**
     * 职位名称
     */
    @ApiModelProperty("职位名称")
    private String professionName;

    /**
     * 职位等级
     */
    @ApiModelProperty("职位等级")
    private Integer level;
    /**
     * 权限等级
     */
    @ApiModelProperty("权限等级")
    private Integer priority;

    /**
     * 职位描述
     */
    @ApiModelProperty("职位描述")
    private String description;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date created;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private Date modified;
}
