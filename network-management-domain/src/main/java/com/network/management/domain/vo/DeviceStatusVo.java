package com.network.management.domain.vo;

import com.network.management.domain.dao.Equipment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Objects;

/**
 * 前端设备信息
 * @author yyc
 * @date 2020/9/15 20:50
 */
@Data
@ApiModel("前端设备状态信息")
@ToString
public class DeviceStatusVo<T> {
    /**
     * 设备关联巷道图id
     */
    @ApiModelProperty("设备关联巷道图id")
    private Integer bordInformationId;

    /**
     * 设备id
     */
    @ApiModelProperty("设备id")
    private Integer equipmentId;

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
     * 位置
     */
    @ApiModelProperty("位置")
    private String position;

    /**
     * 设备图标路径
     */
    @ApiModelProperty("设备图标")
    private String equipmentImgUrl;

    /**
     * 设备状态对象
     */
    @ApiModelProperty("设备状态对象")
    private T statusObj;

    /**
     * 设备状态，1：使用红色图标，2：使用黄色图标，3：使用绿色图标
     */
    @ApiModelProperty("设备状态（1：使用红色图标，2：使用黄色图标，3：使用绿色图标）")
    private Integer status;

    /**
     * 填充DeviceStatusVo对象
     *
     * @param equipment      {@link Equipment}
     */
    public void fillEquipment(Equipment equipment) {
        if(Objects.isNull(equipment)){
            return;
        }
        this.setBordInformationId(equipment.getBordInformationId());
        this.setEquipmentId(equipment.getId());
        this.setIp(equipment.getIp());
        this.setEquipmentType(equipment.getEquipmentType());
        this.setInternalTime(equipment.getInternalTime());
        this.setName(equipment.getName());
        this.setPosition(equipment.getPosition());
//        this.setPassword(equipment.getPassword());
//        this.setUsername(equipment.getUsername());
        this.setX(equipment.getX());
        this.setY(equipment.getY());
        this.setEquipmentImgUrl(equipment.getEquipmentImgUrl());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        DeviceStatusVo<?> that = (DeviceStatusVo<?>) o;
        return Objects.equals(ip, that.ip) && Objects.equals(statusObj, that.statusObj) && Objects.equals(status,
                that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, statusObj, status);
    }
}
