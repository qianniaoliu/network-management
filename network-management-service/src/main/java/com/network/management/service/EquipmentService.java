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

    void add(Equipment equipment);

    void update(Equipment equipment);

    void delete(Set<Integer> ids);

    Equipment get(Integer id);

    List<Equipment> getByBordId(Integer bordId);
}
