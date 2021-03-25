package com.network.management.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 用户授权明细关系表
 * @author yyc
 * @date 2021/3/25 11:25
 */
@Data
@ApiModel("用户授权明细关系")
public class AuthorityRelationVo {
    /**
     * 主键id
     */
    @ApiModelProperty("用户授权明细关系id")
    private Integer id;
    /**
     * 授权id
     */
    @ApiModelProperty("授权id")
    private Integer authorityId;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Integer userId;
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
