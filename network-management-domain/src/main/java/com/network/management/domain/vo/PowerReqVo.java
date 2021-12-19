package com.network.management.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 电源查询请求对象
 * @author yyc
 * @date 2021/12/19 17:23
 */
@Data
@ApiModel("电源查询请求对象")
public class PowerReqVo {
    /**
     * 电源ip
     */
    @ApiModelProperty("电源ip")
    private String ip;
    /**
     * 电源端口
     */
    @ApiModelProperty("电源端口")
    private Integer port;

    /**
     * 操作类型
     * @see com.network.management.domain.enums.PowerCmdEnum
     */
    private Integer type;
}
