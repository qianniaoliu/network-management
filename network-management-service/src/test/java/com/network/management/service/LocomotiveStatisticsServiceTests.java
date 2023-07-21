package com.network.management.service;

import com.network.management.domain.vo.LocomotiveAccessDataVo;
import com.network.management.service.config.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * @author <a href="mailto:cereb.shen@gmail.com">shenlong</a>
 */
@SpringBootTest(classes = TestConfig.class, webEnvironment= SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(value = {"classpath:application-test.properties"})
public class LocomotiveStatisticsServiceTests {

    @Autowired
    private LocomotiveStatisticsService locomotiveStatisticsService;

    @Test
    public void testQueryLocomotiveData(){
        LocomotiveAccessDataVo locomotiveAccessDataVo = locomotiveStatisticsService.queryLocomotiveData();
        Assertions.assertNotNull(locomotiveAccessDataVo);
    }
}
