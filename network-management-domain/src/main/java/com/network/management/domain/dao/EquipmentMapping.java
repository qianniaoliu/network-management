package com.network.management.domain.dao;

import com.network.management.domain.enums.YnEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 设备与设备的拓扑关系
 *
 * @author yusheng
 */
@Data
@ApiModel("设备与设备的拓扑关系")
public class EquipmentMapping {

    /**
     * 映射关系主键id
     */
    @ApiModelProperty("映射关系主键id")
    private Integer id;

    /**
     * 设备关联巷道图id
     */
    @ApiModelProperty("设备关联巷道图id")
    private Integer bordInformationId;

    /**
     * 源设备Id
     */
    @ApiModelProperty("源设备Id")
    private Integer sourceId;

    /**
     * 源ip
     */
    @ApiModelProperty("源ip")
    private String sourceIp;

    /**
     * 目标设备Id
     */
    @ApiModelProperty("目标设备Id")
    private Integer targetId;

    /**
     * 目标ip
     */
    @ApiModelProperty("目标ip")
    private String targetIp;

    /**
     * 有效状态
     */
    @ApiModelProperty("有效状态")
    private Integer yn;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private Date modified;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date created;

    public void initCreateInfo(){
        setYn(YnEnum.YES.getCode());
        setCreated(new Date());
        setModified(new Date());
    }

    public void initModifyInfo(){
        setModified(new Date());
    }
}
