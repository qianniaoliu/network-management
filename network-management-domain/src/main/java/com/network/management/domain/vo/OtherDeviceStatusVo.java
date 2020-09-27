package com.network.management.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 获取其他设备状态数据
 * @author yyc
 * @date 2020/9/15 20:48
 */
@Data
@AllArgsConstructor
@ApiModel("获取其他设备状态数据")
public class OtherDeviceStatusVo{
    /**
     * ping连接状态 {@link com.network.management.domain.enums.YnEnum}
     */
    @ApiModelProperty("ping连接状态")
    private Integer status;
}
