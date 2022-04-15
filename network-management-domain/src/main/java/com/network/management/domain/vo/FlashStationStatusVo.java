package com.network.management.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.Objects;

/**
 * flash界面基站状态数据
 * @author yyc
 * @date 2020/9/15 20:49
 */
@Data
@ApiModel("flash界面基站状态数据")
@ToString
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

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date created;

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        FlashStationStatusVo that = (FlashStationStatusVo) o;
        return Objects.equals(wanStatus, that.wanStatus) && Objects.equals(wanInternet, that.wanInternet)
                && Objects.equals(ipSecSwitch, that.ipSecSwitch) && Objects.equals(ipSecStatus, that.ipSecStatus)
                && Objects.equals(s1Status, that.s1Status) && Objects.equals(cellStatus, that.cellStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wanStatus, wanInternet, ipSecSwitch, ipSecStatus, s1Status, cellStatus);
    }
}
