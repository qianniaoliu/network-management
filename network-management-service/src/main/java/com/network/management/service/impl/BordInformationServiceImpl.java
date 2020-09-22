package com.network.management.service.impl;

import com.network.management.common.exception.Assert;
import com.network.management.domain.dao.BordInformation;
import com.network.management.domain.dao.Equipment;
import com.network.management.domain.dao.EquipmentMapping;
import com.network.management.domain.vo.BordInformationAggregation;
import com.network.management.mapper.BordInformationMapper;
import com.network.management.service.BordInformationService;
import com.network.management.service.EquipmentMappingService;
import com.network.management.service.EquipmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 巷道图服务实现
 *
 * @author yusheng
 */
@Service
public class BordInformationServiceImpl implements BordInformationService {

    private final BordInformationMapper bordInformationMapper;

    private final EquipmentService equipmentService;

    private final EquipmentMappingService equipmentMappingService;

    public BordInformationServiceImpl(BordInformationMapper bordInformationMapper,
                                      EquipmentService equipmentService,
                                      EquipmentMappingService equipmentMappingService) {
        this.bordInformationMapper = bordInformationMapper;
        this.equipmentService = equipmentService;
        this.equipmentMappingService = equipmentMappingService;
    }

    @Override
    public void save(BordInformation bordInformation) {
        Assert.notNull(bordInformation, "bordInformation 对象不能为null");
        if (Objects.isNull(bordInformation.getId())) {
            List<BordInformation> bordInformationList = selectAll();
            if (!CollectionUtils.isEmpty(bordInformationList)) {
                BordInformation existBordInformation = bordInformationList.get(0);
                bordInformation.setId(existBordInformation.getId());
            }
        }
        if (Objects.nonNull(bordInformation.getId())) {
            bordInformation.initModifyInfo();
            bordInformationMapper.updateByKey(bordInformation);
        } else {
            bordInformation.initCreateInfo();
            bordInformationMapper.insert(bordInformation);
        }
    }

    @Override
    @Transactional
    public void updateAll(BordInformationAggregation data) {
        Assert.notNull(data, "BordInformationAggregation 对象不能为null");
        //更新巷道图信息
        BordInformation bordInformation = data.getBordInformation();
        if (Objects.nonNull(bordInformation)) {
            bordInformationMapper.updateByKey(bordInformation);
        }
        /**
         * 新增修改设备信息
         */
        List<Integer> newExistEquipmentId = new ArrayList<>();
        if (!CollectionUtils.isEmpty(data.getEquipments())) {
            data.getEquipments().forEach(equipment -> {
                //id为空则新增
                if (Objects.isNull(equipment.getId())) {
                    equipmentService.add(equipment);
                }
                //id不为空则修改
                else {
                    newExistEquipmentId.add(equipment.getId());
                    equipmentService.update(equipment);
                }
            });
        }
        /**
         * 删除入参里没有的设备信息
         */
        List<Equipment> existEquipments = equipmentService.getByBordId(data.getBordInformation().getId());
        Set<Integer> needDeleteEquipmentIds = existEquipments.stream()
                .map(Equipment::getId)
                .filter(item -> !newExistEquipmentId.contains(item))
                .collect(Collectors.toSet());
        if (!CollectionUtils.isEmpty(needDeleteEquipmentIds)) {
            equipmentService.delete(needDeleteEquipmentIds);
        }

        /**
         * 新增修改设备之间的映射关系
         */
        List<Integer> newExistEquipmentMappingId = new ArrayList<>();
        if (!CollectionUtils.isEmpty(data.getEquipmentMappings())) {
            data.getEquipmentMappings().forEach(equipmentMapping -> {
                //id为空则新增
                if (Objects.isNull(equipmentMapping.getId())) {
                    equipmentMappingService.add(equipmentMapping);
                }
                //id不为空则修改
                else {
                    newExistEquipmentMappingId.add(equipmentMapping.getId());
                    equipmentMappingService.update(equipmentMapping);
                }
            });
        }
        /**
         * 删除入参里没有的设备之间映射关系信息
         */
        List<EquipmentMapping> equipmentMappings = equipmentMappingService.getByBordId(data.getBordInformation().getId());
        Set<Integer> needDeleteEquipmentMappingIds = equipmentMappings.stream()
                .map(EquipmentMapping::getId)
                .filter(item -> !newExistEquipmentMappingId.contains(item))
                .collect(Collectors.toSet());
        if (!CollectionUtils.isEmpty(needDeleteEquipmentMappingIds)) {
            equipmentMappingService.delete(needDeleteEquipmentMappingIds);
        }
    }

    @Override
    public BordInformationAggregation getAll() {
        BordInformationAggregation result = new BordInformationAggregation();
        List<BordInformation> bordInformationList = selectAll();
        if (CollectionUtils.isEmpty(bordInformationList)) {
            return result;
        }
        BordInformation bordInformation = bordInformationList.get(0);
        Integer bordInformationId = bordInformation.getId();
        result.setBordInformation(bordInformation);
        result.setEquipments(equipmentService.getByBordId(bordInformationId));
        result.setEquipmentMappings(equipmentMappingService.getByBordId(bordInformationId));
        return result;
    }

    @Override
    public List<BordInformation> selectAll() {
        List<BordInformation> result = Optional.ofNullable(bordInformationMapper.selectAll())
                .orElse(Collections.emptyList());
        return result.stream()
                .map(BordInformation::clearExtendInfo)
                .collect(Collectors.toList());
    }
}
