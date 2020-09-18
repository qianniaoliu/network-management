package com.network.management.domain.vo;

import com.network.management.domain.dao.BordInformation;
import com.network.management.domain.dao.Equipment;
import com.network.management.domain.dao.EquipmentMapping;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 巷道图信息聚合
 *
 * @author yusheng
 */
@Data
@ApiModel("巷道图信息聚合")
public class BordInformationAggregation {

    /**
     * 巷道基本信息
     */
    @ApiModelProperty("巷道图基本信息")
    private BordInformation bordInformation;

    /**
     * 巷道关联的设备信息
     */
    @ApiModelProperty("巷道关联的设备信息")
    private List<Equipment> equipments;

    /**
     * 巷道上设备与设备之间的映射信息
     */
    @ApiModelProperty("巷道上设备与设备之间的映射信息")
    private List<EquipmentMapping> equipmentMappings;

}
