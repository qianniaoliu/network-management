package com.network.management.mapper;

import com.network.management.domain.dao.Equipment;

import java.util.List;

/**
 * 设备信息mapper
 *
 * @author yusheng
 */
public interface EquipmentMapper {

    /**
     * 新增设备信息
     * @param equipment 设备信息
     * @return 设备id
     */
    Integer insert(Equipment equipment);

    /**
     * 更新设备信息
     * @param equipment 设备信息
     */
    void updateByPrimaryKeySelective(Equipment equipment);

    /**
     * 删除设备信息
     * @param id 设备id
     */
    void deleteByPrimaryKey(Integer id);

    /**
     * 通过主键id查询设备信息
     * @param id 主键id
     * @return 设备信息
     */
    Equipment selectByPrimaryKey(Integer id);

    /**
     * 通过巷道图id获取设备信息集合
     * @param bordId 巷道图id
     * @return 设备信息集合
     */
    List<Equipment> getByBordId(Integer bordId);

}
