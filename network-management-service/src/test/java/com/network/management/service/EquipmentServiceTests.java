package com.network.management.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import com.network.management.domain.dao.Equipment;
import com.network.management.domain.vo.DeviceStatusVo;
import com.network.management.service.config.TestConfig;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

/**
 * 设备信息单元测试
 *
 * @author yusheng
 */
@SpringBootTest(classes = TestConfig.class, webEnvironment= SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(value = {"classpath:application-test.properties"})
public class EquipmentServiceTests {

    @Autowired
    private EquipmentService equipmentService;


    @Test
    public void testAdd(){
        Equipment equipment = new Equipment();
        equipment.setBordInformationId(5);
        equipment.setIp("127.0.0.98");
        equipment.setName("6G信号发射台");
        equipment.setEquipmentType(1);
        equipment.setInternalTime(110000L);
        equipment.setX(100);
        equipment.setY(300);
        equipment.setUsername("test1");
        equipment.setPassword("test1");
        Integer equipmentId = equipmentService.add(equipment);
        Assert.assertNotNull(equipmentId);
    }

    @Test
    public void testUpdate(){
        Equipment equipment = new Equipment();
        equipment.setId(1);
        equipment.setIp("127.0.0.7");
        equipment.setName("6G信号发射台");
        equipment.setEquipmentType(3);
        equipment.setInternalTime(210000L);
        equipment.setX(100);
        equipment.setY(300);
        equipment.setUsername("test");
        equipment.setPassword("test");
        equipmentService.update(equipment);
    }


    @Test
    public void testGet(){
        Equipment equipment = equipmentService.get(1);
        Assert.assertNotNull(equipment);
    }

    @Test
    public void testDelete(){
        equipmentService.delete(Sets.newHashSet(1));
    }


    @Test
    public void testGetByBordId(){
        List<Equipment> equipments = equipmentService.getByBordId(5);
        Assert.assertNotNull(equipments);
    }

    @Test
    public void testQueryStatus(){
        DeviceStatusVo<?> deviceStatusVo = equipmentService.queryStatus(3);
        Assert.assertNotNull(deviceStatusVo);
    }

}
