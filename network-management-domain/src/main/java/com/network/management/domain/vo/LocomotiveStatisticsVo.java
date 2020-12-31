package com.network.management.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 机车统计数据
 * @author yyc
 * @date 2020/12/31 18:56
 */
@Data
@ApiModel("机车统计数量")
public class LocomotiveStatisticsVo {
    /**
     * 机车总数
     */
    @ApiModelProperty("机车总数")
    private Integer totalNumber;
    /**
     * 昨天机车总数
     */
    @ApiModelProperty("昨天机车总数")
    private Integer yesterdayNumber;
    /**
     * 今天机车总数
     */
    @ApiModelProperty("今天机车总数")
    private Integer todayNumber;
}
