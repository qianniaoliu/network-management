package com.network.management.service.impl;

import com.network.management.EquipmentMapping;
import com.network.management.exception.Assert;
import com.network.management.mapper.EquipmentMappingMapper;
import com.network.management.service.EquipmentMappingService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 设备之间映射关系
 *
 * @author yusheng
 */
@Service
public class EquipmentMappingServiceImpl implements EquipmentMappingService {

    private final EquipmentMappingMapper equipmentMappingMapper;

    public EquipmentMappingServiceImpl(EquipmentMappingMapper equipmentMappingMapper) {
        this.equipmentMappingMapper = equipmentMappingMapper;
    }

    @Override
    public void add(EquipmentMapping equipmentMapping) {
        Assert.notNull(equipmentMapping, "equipmentMapping不能为空");
        equipmentMappingMapper.insert(equipmentMapping);
    }

    @Override
    public void update(EquipmentMapping equipmentMapping) {
        Assert.notNull(equipmentMapping, "equipmentMapping不能为空");
        equipmentMappingMapper.updateByPrimaryKeySelective(equipmentMapping);
    }

    @Override
    public void delete(Set<Integer> ids) {
        Assert.notNull(ids, "ids不能为空");
        ids.forEach(equipmentMappingMapper::deleteByPrimaryKey);
    }

    @Override
    public List<EquipmentMapping> getByBordId(Integer bordId) {
        Assert.notNull(bordId, "bordId不能为空");
        return Optional.ofNullable(equipmentMappingMapper.getByBordId(bordId))
                .orElse(Collections.emptyList());
    }
}
