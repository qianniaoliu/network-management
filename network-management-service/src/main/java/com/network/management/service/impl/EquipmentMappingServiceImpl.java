package com.network.management.service.impl;

import com.network.management.EquipmentMapping;
import com.network.management.service.EquipmentMappingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 设备之间映射关系
 *
 * @author yusheng
 */
@Service
public class EquipmentMappingServiceImpl implements EquipmentMappingService {
    @Override
    public void add(EquipmentMapping equipmentMapping) {

    }

    @Override
    public void update(EquipmentMapping equipmentMapping) {

    }

    @Override
    public void delete(Set<Integer> ids) {

    }

    @Override
    public List<EquipmentMapping> getByBordId(Integer bordId) {
        return null;
    }
}
