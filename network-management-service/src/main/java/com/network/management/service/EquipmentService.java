package com.network.management.service;

import com.network.management.Equipment;

import java.util.List;
import java.util.Set;

/**
 * 设备信息服务
 *
 * @author yusheng
 */
public interface EquipmentService {

    /**
     * 新增设备信息
     * @param equipment 设备信息
     */
    void add(Equipment equipment);

    /**
     * 修改设备信息
     * @param equipment 设备信息
     */
    void update(Equipment equipment);

    /**
     * 删除设备信息
     * @param ids 设备id集合
     */
    void delete(Set<Integer> ids);

    /**
     * 获取单个设备信息
     * @param id 设备id
     * @return 设备信息
     */
    Equipment get(Integer id);

    /**
     * 通过巷道图id获取所有的设备信息
     * @param bordId 巷道图id
     * @return 设备信息集合
     */
    List<Equipment> getByBordId(Integer bordId);

    List<Equipment> getAllEquipments();
}
