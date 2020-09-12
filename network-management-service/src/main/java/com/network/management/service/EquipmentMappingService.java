package com.network.management.service;

import com.network.management.EquipmentMapping;

import java.util.List;
import java.util.Set;

/**
 * 设备映射关系服务
 *
 * @author yusheng
 */
public interface EquipmentMappingService {

    /**
     * 新增设备映射关系
     * @param equipmentMapping
     */
    void add(EquipmentMapping equipmentMapping);

    /**
     * 修改设备映射关系
     * @param equipmentMapping
     */
    void update(EquipmentMapping equipmentMapping);

    /**
     * 删除设备映射关系
     * @param ids 主键id
     */
    void delete(Set<Integer> ids);

    /**
     * 通过巷道图id获取设备映射关系
     * @param bordId
     * @return
     */
    List<EquipmentMapping> getByBordId(Integer bordId);
}
