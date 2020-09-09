package com.network.management.service;

import com.network.management.Equipment;

import java.util.List;

/**
 * @author yusheng
 */
public interface EquipmentService {

    void add(Equipment equipment);

    void update(Equipment equipment);

    void delete(Integer id);

    Equipment get(Integer id);

    List<Equipment> getByBordId(Integer bordId);
}
