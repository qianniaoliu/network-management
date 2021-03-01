package com.network.management.service;

import com.network.management.domain.search.Page;
import com.network.management.domain.search.ProfessionSearch;
import com.network.management.domain.vo.ProfessionVo;
import com.network.management.service.config.TestConfig;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

/**
 * 职位服务测试类
 * @author yyc
 * @date 2021/03/01 15:21
 */
@SpringBootTest(classes = TestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(value = {"classpath:application-test.properties"})
public class ProfessionServiceTests {

    @Autowired
    private ProfessionService professionService;

    @Test
    public void testAdd() {
        ProfessionVo professionVo = new ProfessionVo();
        professionVo.setDescription("test");
        professionVo.setProfessionName("部门经理");
        professionVo.setPriority(1);
        professionVo.setLevel(1);
        professionService.add(professionVo);
    }

    @Test
    public void testSearch() {
        ProfessionSearch search = new ProfessionSearch();
        search.setProfessionName("部门经理");
        search.setPageSize(2);
        search.setCurrentPage(1);
        Page<ProfessionVo> page = professionService.search(search);
        Assert.assertNotNull("职位数据不为空", page);
    }

    @Test
    public void testUpdate(){
        ProfessionVo professionVo = new ProfessionVo();
        professionVo.setDescription("test3");
        professionVo.setProfessionName("部门经理3");
        professionVo.setPriority(5);
        professionVo.setLevel(5);
        professionVo.setId(1);
        professionService.update(professionVo);
    }

    @Test
    public void testGet(){
        ProfessionVo professionVo = professionService.get(1);
        Assert.assertNotNull("职位数据不为空", professionVo);
    }

    @Test
    public void testQueryAll(){
        List<ProfessionVo> departmentVos = professionService.queryAllProfessions();
        Assert.assertNotNull("职位数据不为空", departmentVos);
    }

    @Test
    public void testDelete(){
        professionService.delete(1);
    }
}
