package com.network.management.service;

import com.google.common.collect.Lists;
import com.network.management.domain.dao.BordInformation;
import com.network.management.domain.dao.Equipment;
import com.network.management.domain.dao.EquipmentMapping;
import com.network.management.domain.vo.BordInformationAggregation;
import com.network.management.service.config.TestConfig;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

/**
 * 巷道图单元测试
 *
 * @author yusheng
 */
@SpringBootTest(classes = TestConfig.class, webEnvironment= SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(value = {"classpath:application-test.properties"})
public class BordInformationServiceTests {

    @Autowired
    private BordInformationService bordInformationService;


    @Test
    public void testSave(){
        BordInformation bordInformation = new BordInformation();
        bordInformation.setName("测试巷道图名称1");
        bordInformation.setUrl("/bord-img.jpg");
        bordInformationService.save(bordInformation);
    }

    @Test
    public void testUpdateAll(){
        BordInformationAggregation data = new BordInformationAggregation();

        BordInformation bordInformation = new BordInformation();
        bordInformation.setId(1);
        bordInformation.setName("修改标题");
        data.setBordInformation(bordInformation);

        Equipment equipment1 = new Equipment();
        equipment1.setId(1);
        equipment1.setX(100);
        equipment1.setY(200);

        Equipment equipment2 = new Equipment();
        equipment2.setId(2);
        equipment2.setX(200);
        equipment2.setY(100);
        List<Equipment> equipmentList = Lists.newArrayList(equipment1, equipment2);
        data.setEquipments(equipmentList);

        EquipmentMapping equipmentMapping = new EquipmentMapping();
        equipmentMapping.setBordInformationId(1);
        equipmentMapping.setTargetId(1);
        equipmentMapping.setSourceId(2);
        List<EquipmentMapping> equipmentMappings = Lists.newArrayList(equipmentMapping);
        data.setEquipmentMappings(equipmentMappings);
        bordInformationService.updateAll(data);
    }

    @Test
    public void testGetAll(){
        BordInformationAggregation bordInformationAggregation1 = bordInformationService.getAll(110);
        Assert.assertNotNull(bordInformationAggregation1);

        BordInformationAggregation bordInformationAggregation2 = bordInformationService.getAll(5);
        Assert.assertNotNull(bordInformationAggregation2);

    }
}
