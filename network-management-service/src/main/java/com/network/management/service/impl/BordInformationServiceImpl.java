package com.network.management.service.impl;

import com.network.management.BordInformation;
import com.network.management.Equipment;
import com.network.management.EquipmentMapping;
import com.network.management.enums.YnEnum;
import com.network.management.exception.Assert;
import com.network.management.mapper.BordInformationMapper;
import com.network.management.service.BordInformationService;
import com.network.management.service.EquipmentMappingService;
import com.network.management.service.EquipmentService;
import com.network.management.vo.BordInformationAggregation;
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
        bordInformation.setModified(new Date());
        if(Objects.nonNull(bordInformation.getId())){
            bordInformationMapper.updateByKey(bordInformation);
        }else {
            bordInformation.setYn(YnEnum.YES.getCode());
            bordInformation.setCreated(new Date());
            bordInformationMapper.insert(bordInformation);
        }
    }

    @Override
    @Transactional
    public void update(BordInformationAggregation data) {
        Assert.notNull(data, "BordInformationAggregation 对象不能为null");
        /**
         * 新增修改设备信息
         */
        List<Integer> newExistEquipmentId = new ArrayList<>();
        if(!CollectionUtils.isEmpty(data.getEquipments())){
            data.getEquipments().forEach(equipment -> {
                //id为空则新增
                if(Objects.isNull(equipment.getId())){
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
        if(!CollectionUtils.isEmpty(needDeleteEquipmentIds)){
            equipmentService.delete(needDeleteEquipmentIds);
        }

        /**
         * 新增修改设备之间的映射关系
         */
        List<Integer> newExistEquipmentMappingId = new ArrayList<>();
        if(!CollectionUtils.isEmpty(data.getEquipmentMappings())){
            data.getEquipmentMappings().forEach(equipmentMapping -> {
                //id为空则新增
                if(Objects.isNull(equipmentMapping.getId())){
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
        if(!CollectionUtils.isEmpty(needDeleteEquipmentMappingIds)){
            equipmentMappingService.delete(needDeleteEquipmentMappingIds);
        }
    }

    @Override
    public BordInformationAggregation get(Integer bordInformationId) {
        BordInformationAggregation result = new BordInformationAggregation();
        result.setBordInformation(bordInformationMapper.selectByPrimaryKey(bordInformationId));
        result.setEquipments(equipmentService.getByBordId(bordInformationId));
        result.setEquipmentMappings(equipmentMappingService.getByBordId(bordInformationId));
        return result;
    }
}
