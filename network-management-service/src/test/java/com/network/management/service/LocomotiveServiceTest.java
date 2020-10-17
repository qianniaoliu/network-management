package com.network.management.service;

import com.alibaba.fastjson.JSON;
import com.network.management.domain.search.LocomotiveSearch;
import com.network.management.domain.search.Page;
import com.network.management.domain.vo.LocomotiveVo;
import com.network.management.service.config.TestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Map;

/**
 * 机车服务测试类
 * @author yyc
 * @date 2020/10/17 11:55
 */
@SpringBootTest(classes = TestConfig.class, webEnvironment= SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(value = {"classpath:application-test.properties"})
@Slf4j
public class LocomotiveServiceTest {
    @Autowired
    private LocomotiveService locomotiveService;

    @Test
    public void testSave(){
        LocomotiveVo locomotiveVo = new LocomotiveVo();
        locomotiveVo.setUeIp("172.20.80.31");
        locomotiveVo.setNum(1);
        locomotiveVo.setDesc("test1");
        locomotiveService.saveLocomotive(locomotiveVo);
    }

    @Test
    public void testUpdate(){
        LocomotiveVo locomotiveVo = new LocomotiveVo();
        locomotiveVo.setUeIp("172.20.80.28");
        locomotiveVo.setNum(3);
        locomotiveVo.setDesc("test3");
        locomotiveVo.setId(3);
        locomotiveService.updateLocomotive(locomotiveVo);
    }

    @Test
    public void testDelete(){
        locomotiveService.delete(2);
    }

    @Test
    public void testQueryAllLocomotives(){
        List<LocomotiveVo> locomotiveVos = locomotiveService.queryAllLocomotiveVos();
        log.info("查询所有机车数据:{}", JSON.toJSONString(locomotiveVos));
    }

    @Test
    public void testSearch(){
        LocomotiveSearch search = new LocomotiveSearch();
        search.setPageSize(10);
        search.setCurrentPage(1);
        search.setUeIp("172.20.80.31");
        Page<LocomotiveVo> locomotiveVos = locomotiveService.search(search);
        log.info("分页机车数据:{}", JSON.toJSONString(locomotiveVos));
    }

    @Test
    public void testQueryLocomotiveStatus(){
        Map<String, List<LocomotiveVo>> locomotiveMap = locomotiveService.queryLocomotiveStatus();
        log.info("查询基站对应机车的状态:{}", JSON.toJSONString(locomotiveMap));
    }
}
