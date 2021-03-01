package com.network.management.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 通讯录对象
 * @author yyc
 * @date 2021/2/27 14:02
 */
@Data
@ApiModel("通讯录信息")
public class AddressBookVo {
    /**
     * id
     */
    @ApiModelProperty("通讯录id")
    private Integer id;
    /**
     * 通讯录名称
     */
    @ApiModelProperty("通讯录名称")
    private String addressBookName;
    /**
     * 部门id
     */
    @ApiModelProperty("部门id")
    private Integer departmentId;
}
