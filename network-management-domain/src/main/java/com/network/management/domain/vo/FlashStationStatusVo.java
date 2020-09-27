package com.network.management.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * flash界面基站状态数据
 * @author yyc
 * @date 2020/9/15 20:49
 */
@Data
@ApiModel("flash界面基站状态数据")
public class FlashStationStatusVo{
    /**
     * wan口状态
     */
    @ApiModelProperty("wan口状态")
    private Integer wanStatus;
    /**
     * wan口连接状态
     */
    @ApiModelProperty("wan口连接状态")
    private Integer wanInternet;
    /**
     * ipsec开关状态
     */
    @ApiModelProperty("ipsec开关状态")
    private Integer ipSecSwitch;
    /**
     * ipsec状态
     */
    @ApiModelProperty("ipsec状态")
    private Integer ipSecStatus;
    /**
     * s1状态
     */
    @ApiModelProperty("s1状态")
    private Integer s1Status;
    /**
     * 小区状态
     */
    @ApiModelProperty("小区状态")
    private Integer cellStatus;
}
