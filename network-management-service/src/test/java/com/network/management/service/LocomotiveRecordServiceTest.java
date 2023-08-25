package com.network.management.service;

import com.network.management.domain.vo.LocomotiveRecordVo;
import com.network.management.service.config.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;

/**
 * @author <a href="mailto:cereb.shen@gmail.com">shenlong</a>
 */
@SpringBootTest(classes = TestConfig.class, webEnvironment= SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(value = {"classpath:application-test.properties"})
public class LocomotiveRecordServiceTest {

    @Autowired
    private LocomotiveRecordService locomotiveRecordService;

    @Test
    public void testSave(){
        LocomotiveRecordVo recordVo1 = new LocomotiveRecordVo();
        recordVo1.setVehicleType("大货车");
        recordVo1.setDirection("进场");
        recordVo1.setSectionNumber(2);
        recordVo1.setLocation("南翼");
        recordVo1.setOccurDate(new Date());
        Assertions.assertTrue(locomotiveRecordService.save(recordVo1));

        LocomotiveRecordVo recordVo2 = new LocomotiveRecordVo();
        recordVo2.setVehicleType("大货车");
        recordVo2.setDirection("出场");
        recordVo2.setSectionNumber(2);
        recordVo2.setLocation("南翼");
        recordVo2.setOccurDate(new Date());
        Assertions.assertTrue(locomotiveRecordService.save(recordVo2));

        LocomotiveRecordVo recordVo3 = new LocomotiveRecordVo();
        recordVo3.setVehicleType("大货车");
        recordVo3.setDirection("进场");
        recordVo3.setSectionNumber(2);
        recordVo3.setLocation("北翼");
        recordVo3.setOccurDate(new Date());
        Assertions.assertTrue(locomotiveRecordService.save(recordVo3));

        LocomotiveRecordVo recordVo4 = new LocomotiveRecordVo();
        recordVo4.setVehicleType("大货车");
        recordVo4.setDirection("出场");
        recordVo4.setSectionNumber(2);
        recordVo4.setLocation("北翼");
        recordVo4.setOccurDate(new Date());
        Assertions.assertTrue(locomotiveRecordService.save(recordVo4));
    }
}
