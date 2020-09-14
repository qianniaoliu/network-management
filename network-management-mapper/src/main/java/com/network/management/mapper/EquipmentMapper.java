package com.network.management.mapper;

import com.network.management.domain.dao.Equipment;

import java.util.List;

/**
 * 设备信息mapper
 *
 * @author yusheng
 */
public interface EquipmentMapper {

    Integer insert(Equipment equipment);

    void updateByPrimaryKeySelective(Equipment equipment);

    void deleteByPrimaryKey(Integer id);

    Equipment selectByPrimaryKey(Integer id);

    List<Equipment> getByBordId(Integer bordId);

}
