package com.network.management.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import com.network.management.domain.dao.Equipment;
import com.network.management.domain.excel.DeviceStatusData;
import com.network.management.domain.search.EquipmentStatusSearch;
import com.network.management.domain.search.Page;
import com.network.management.domain.vo.DeviceStatusVo;
import com.network.management.service.config.TestConfig;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;
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
        equipment.setName("7G信号发射台");
        equipment.setEquipmentType(1);
        equipment.setInternalTime(110000L);
        equipment.setX(100);
        equipment.setY(300);
        equipment.setUsername("test1");
        equipment.setPassword("test1");
        equipment.setPosition("美国旧金山");
        equipment.setEquipmentImgUrl("/equipment-3213532.jpg");
        Integer equipmentId = equipmentService.add(equipment);
        Assert.assertNotNull(equipmentId);
    }

    @Test
    public void testUpdate(){
        Equipment equipment = new Equipment();
        equipment.setId(86);
        equipment.setIp("127.0.0.7");
        equipment.setName("7G信号发射台");
        equipment.setEquipmentType(3);
        equipment.setInternalTime(210000L);
        equipment.setX(100);
        equipment.setY(300);
        equipment.setUsername("test");
        equipment.setPassword("test");
        equipment.setPosition("美国曼哈顿");
        equipment.setEquipmentImgUrl("/equipment-110110.jpg");
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

    @Test
    public void testSearchDeviceStatus(){
        EquipmentStatusSearch param = new EquipmentStatusSearch();
        param.setCurrentPage(1);
        param.setPageSize(20);
        param.setIp("127.0.0.98");
        param.setEquipmentType(1);
        param.setStartTime(new Date());
        Page<DeviceStatusVo> data = equipmentService.searchDeviceStatus(param);
        Assert.assertNotNull(data);
    }

    @Test
    public void testSearchExportData(){
        EquipmentStatusSearch param = new EquipmentStatusSearch();
        param.setCurrentPage(1);
        param.setPageSize(20);
        param.setIp("172.16.11.106");
        param.setEquipmentType(1);
//        param.setStartTime(new Date());
        DeviceStatusData data = equipmentService.searchExportData(param);
        Assert.assertNotNull(data);
    }

}
