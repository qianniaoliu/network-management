package com.network.management.mapper;

import com.network.management.Equipment;

import java.util.List;
import java.util.Set;

/**
 * 设备信息mapper
 *
 * @author yusheng
 */
public interface EquipmentMapper {

    Integer insert(Equipment equipment);

    void updateByPrimaryKeySelective(Equipment equipment);

    void deleteByPrimaryKey(Integer id);

    Equipment get(Integer id);

    List<Equipment> getByBordId(Integer bordId);

}
