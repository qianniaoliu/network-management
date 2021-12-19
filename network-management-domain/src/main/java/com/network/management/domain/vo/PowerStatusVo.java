package com.network.management.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 电源状态实体类
 * @author yyc
 * @date 2021/12/19 17:10
 */
@Data
@ApiModel("电源状态数据")
public class PowerStatusVo {
    /**
     * 电源交直流状态
     * @see com.network.management.domain.enums.PowerStatusEnum
     */
    @ApiModelProperty("电源交直流状态")
    private Integer status;

    /**
     * 电源电量
     */
    @ApiModelProperty("电源电量")
    private Integer powerNumber;
}
