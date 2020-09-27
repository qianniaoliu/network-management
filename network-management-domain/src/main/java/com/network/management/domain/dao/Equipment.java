package com.network.management.domain.dao;

import com.network.management.domain.enums.YnEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 设备信息
 *
 * @author yusheng
 */
@Data
@ApiModel("设备信息")
public class Equipment {

    /**
     * 设备id
     */
    @ApiModelProperty("设备id")
    private Integer id;

    /**
     * 设备关联巷道图id
     */
    @ApiModelProperty("设备关联巷道图id")
    private Integer bordInformationId;

    /**
     * 设备ip
     */
    @ApiModelProperty("设备ip")
    private String ip;

    /**
     * 设备名称
     */
    @ApiModelProperty("设备名称")
    private String name;

    /**
     * 设备类型
     * @see com.network.management.domain.enums.DeviceTypeEnum
     */
    @ApiModelProperty("设备类型")
    private Integer equipmentType;

    /**
     * 数据采集间隔时间
     */
    @ApiModelProperty("数据采集间隔时间")
    private Long internalTime;

    /**
     * x坐标
     */
    @ApiModelProperty("x坐标")
    private Integer x;

    /**
     * y坐标
     */
    @ApiModelProperty("y坐标")
    private Integer y;

    /**
     * 用户名（只有基站才显示）
     */
    @ApiModelProperty("用户名（只有基站才显示）")
    private String username;

    /**
     * 密码（只有基站才显示）
     */
    @ApiModelProperty("密码（只有基站才显示）")
    private String password;

    /**
     * 设备位置
     */
    @ApiModelProperty("设备位置")
    private String position;

    /**
     * 设备图标路径
     */
    @ApiModelProperty("设备图标")
    private String equipmentImgUrl;

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
