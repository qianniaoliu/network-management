package com.network.management.service;

import com.google.common.collect.Sets;
import com.network.management.domain.dao.EquipmentMapping;
import com.network.management.service.config.TestConfig;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

/**
 * 设备与设备之间的关系映射
 *
 * @author yusheng
 */
@SpringBootTest(classes = TestConfig.class, webEnvironment= SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(value = {"classpath:application-test.properties"})
public class EquipmentMappingServiceTests {

    @Autowired
    private EquipmentMappingService equipmentMappingService;

    @Test
    public void testAdd(){
        EquipmentMapping equipmentMapping = new EquipmentMapping();
        equipmentMapping.setBordInformationId(5);
        equipmentMapping.setSourceId(1);
        equipmentMapping.setTargetId(2);
        Integer id = equipmentMappingService.add(equipmentMapping);
        Assert.assertNotNull(id);
    }

    @Test
    public void testUpdate(){
        EquipmentMapping equipmentMapping = new EquipmentMapping();
        equipmentMapping.setId(1);
        equipmentMapping.setSourceId(1);
        equipmentMapping.setTargetId(3);
        equipmentMappingService.update(equipmentMapping);
    }

    @Test
    public void testDelete(){
        equipmentMappingService.delete(Sets.newHashSet(1));
    }

    @Test
    public void testGetByBordId(){
        List<EquipmentMapping> equipmentMappings = equipmentMappingService.getByBordId(5);
        Assert.assertNotNull(equipmentMappings);
    }

}
