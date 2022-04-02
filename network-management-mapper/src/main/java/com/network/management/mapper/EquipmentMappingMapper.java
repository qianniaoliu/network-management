package com.network.management.mapper;

import com.network.management.domain.dao.EquipmentMapping;

import java.util.List;

/**
 * 设备与设备之间的映射关系mapper
 *
 * @author yusheng
 */
public interface EquipmentMappingMapper {

    /**
     * 新增设备映射关系
     * @param equipmentMapping
     */
    Integer insert(EquipmentMapping equipmentMapping);

    /**
     * 修改设备映射关系
     * @param equipmentMapping
     */
    void updateByPrimaryKeySelective(EquipmentMapping equipmentMapping);

    /**
     * 删除设备映射关系
     * @param id 主键id
     */
    void deleteByPrimaryKey(Integer id);

    /**
     * 通过巷道图id获取设备映射关系
     * @param bordId
     * @return
     */
    List<EquipmentMapping> getByBordId(Integer bordId);
}