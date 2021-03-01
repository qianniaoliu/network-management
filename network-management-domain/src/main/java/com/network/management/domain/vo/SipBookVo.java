package com.network.management.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 通讯录sip
 * @author yyc
 * @date 2021/2/28 10:50
 */
@Data
@ApiModel("通讯录账号信息")
public class SipBookVo {
    /**
     * id
     */
    @ApiModelProperty("通讯录账号id")
    private Integer id;
    /**
     * 通讯录id
     */
    @ApiModelProperty("通讯录id")
    private Integer addressBookId;
    /**
     * sip(调度系统id)
     */
    @ApiModelProperty("sip(调度系统id)")
    private Integer sip;
    /**
     * sip用户名称(调度系统)
     */
    @ApiModelProperty("sip用户名称(调度系统)")
    private String sipName;
}
