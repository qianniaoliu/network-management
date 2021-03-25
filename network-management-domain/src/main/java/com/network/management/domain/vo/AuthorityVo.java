package com.network.management.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 授权明细表
 * @author yyc
 * @date 2021/3/25 11:24
 */
@Data
@ApiModel("授权明细")
public class AuthorityVo {
    /**
     * 主键id
     */
    @ApiModelProperty("授权明细id")
    private Integer id;
    /**
     * 描述
     */
    @ApiModelProperty("授权明细")
    private String desc;

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
