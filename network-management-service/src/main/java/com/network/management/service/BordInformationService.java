package com.network.management.service;

import com.network.management.BordInformation;
import com.network.management.Equipment;
import com.network.management.vo.BordInformationAggregation;

/**
 * 巷道信息服务
 *
 * @author yusheng
 */
public interface BordInformationService {

    /**
     * 保存单个巷道图基本信息
     * @param bordInformation
     */
    void save(BordInformation bordInformation);

    /**
     * 完整保存整个巷道图信息
     * @param data 巷道图聚合信息
     */
    void updateAll(BordInformationAggregation data);


    /**
     * 获取整个巷道图信息
     * @param bordInformationId 巷道图id
     * @return 巷道图聚合信息
     * @see BordInformationAggregation
     */
    BordInformationAggregation getAll(Integer bordInformationId);

    /**
     * 获取单个设备信息
     * @param equipmentId 设备信息id
     * @return 设备信息
     * @see Equipment
     */
    Equipment getByEquipmentId(Integer equipmentId);

}
