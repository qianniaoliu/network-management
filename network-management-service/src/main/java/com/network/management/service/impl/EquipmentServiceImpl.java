package com.network.management.service.impl;

import com.network.management.Equipment;
import com.network.management.exception.Assert;
import com.network.management.mapper.EquipmentMapper;
import com.network.management.service.EquipmentService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author yusheng
 */
@Service
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentMapper equipmentMapper;

    public EquipmentServiceImpl(EquipmentMapper equipmentMapper) {
        this.equipmentMapper = equipmentMapper;
    }

    @Override
    public void add(Equipment equipment) {
        Assert.notNull(equipment, "equipment对象不能为空");
        equipmentMapper.insert(equipment);
    }

    @Override
    public void update(Equipment equipment) {
        Assert.notNull(equipment, "equipment对象不能为空");
        equipmentMapper.updateByPrimaryKeySelective(equipment);
    }

    @Override
    public void delete(Set<Integer> ids) {
        Assert.notNull(ids, "ids对象不能为空");
        ids.forEach(equipmentMapper::deleteByPrimaryKey);
    }

    @Override
    public Equipment get(Integer id) {
        Assert.notNull(id, "id对象不能为空");
        return equipmentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Equipment> getByBordId(Integer bordId) {
        List<Equipment> equipments = equipmentMapper.getByBordId(bordId);
        return Optional.ofNullable(equipments).orElse(Collections.emptyList());
    }
}
