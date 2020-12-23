package com.network.management.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 机车状态数据
 * @author yyc
 * @date 2020/10/14 22:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("机车状态数据")
public class LocomotiveVo {
    /**
     * 机车主键id
     */
    @ApiModelProperty("机车主键id")
    private Integer id;
    /**
     * 机车ip
     */
    @ApiModelProperty("机车ip")
    private String ueIp;
    /**
     * 机车编号
     */
    @ApiModelProperty("机车编号")
    private Integer num;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String desc;
    /**
     * 机车状态
     */
    @ApiModelProperty("机车状态")
    private Integer status;
    /**
     * 基站ip
     */
    @ApiModelProperty("基站ip")
    private String eNodeBIP;

    /**
     * 机车imsi
     */
    @ApiModelProperty("机车imsi")
    private String imsi;
}
