package com.network.management.service;

import com.network.management.domain.search.DepartmentSearch;
import com.network.management.domain.search.Page;
import com.network.management.domain.vo.DepartmentVo;
import com.network.management.service.config.TestConfig;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

/**
 * 部门服务测试类
 * @author yyc
 * @date 2021/03/01 15:52
 */
@SpringBootTest(classes = TestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(value = {"classpath:application-test.properties"})
public class DepartmentServiceTests {

    @Autowired
    private DepartmentService departmentService;

    @Test
    public void testAdd() {
        DepartmentVo departmentVo = new DepartmentVo();
        departmentVo.setParentId(0);
        departmentVo.setDepartmentName("test01");
        departmentService.add(departmentVo);
    }

    @Test
    public void testSearch() {
        DepartmentSearch search = new DepartmentSearch();
        search.setDepartmentName("test01");
        search.setPageSize(2);
        search.setCurrentPage(1);
        Page<DepartmentVo> page = departmentService.search(search);
        Assert.assertNotNull("部门数据不为空", page);
    }

    @Test
    public void testUpdate(){
        DepartmentVo departmentVo = new DepartmentVo();
        departmentVo.setParentId(2);
        departmentVo.setDepartmentName("test03");
        departmentVo.setId(1);
        departmentService.update(departmentVo);
    }

    @Test
    public void testGet(){
        DepartmentVo departmentVo = departmentService.get(1);
        Assert.assertNotNull("部门信息不能为空", departmentVo);
    }

    @Test
    public void testQueryAll(){
        List<DepartmentVo> departmentVos = departmentService.queryAllDepartment();
        Assert.assertNotNull("部门信息不能为空", departmentVos);
    }

    @Test
    public void testDelete(){
        departmentService.delete(1);
    }
}
