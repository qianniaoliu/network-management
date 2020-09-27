package com.network.management.domain.search;

import com.network.management.common.exception.IllegalParamException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.Objects;

/**
 * @author yusheng
 */
@Data
@ApiModel("设备状态搜索对象")
public class EquipmentStatusSearch {

    /**
     * 设备ip
     */
    @ApiModelProperty("设备ip")
    private String ip;

    /**
     * 设备类型
     */
    @ApiModelProperty("设备类型")
    private Integer equipmentType;

    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    private Date startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    private String endTime;

    /**
     * 分页大小
     */
    @ApiModelProperty("分页大小")
    private Integer pageSize;

    /**
     * 当前页
     */
    @ApiModelProperty("当前页")
    private Integer currentPage;

    private Integer getStartIndex() {
        if (Objects.isNull(currentPage)
                || Objects.isNull(pageSize)) {
            return null;
        }
        return (currentPage - 1) * pageSize;
    }


    public void checkParams() {
        if (Objects.isNull(this.equipmentType)) {
            throw new IllegalParamException("设备类型不能为空!");
        }
        if(Objects.isNull(this.currentPage)){
            throw new IllegalParamException("当前页码不能为空!");
        }
        if(Objects.isNull(this.pageSize)){
            throw new IllegalParamException("当前分页大小不能为空!");
        }
    }
}
