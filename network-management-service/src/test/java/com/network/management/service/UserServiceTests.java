package com.network.management.service;

import com.network.management.domain.search.Page;
import com.network.management.domain.search.UserSearch;
import com.network.management.domain.dao.User;
import com.network.management.domain.vo.RegistryVo;
import com.network.management.service.config.TestConfig;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * 用户信息单元测试
 *
 * @author yusheng
 */
@SpringBootTest(classes = TestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(value = {"classpath:application-test.properties"})
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Test
    public void testRegistry() {
        RegistryVo registryVo = new RegistryVo();
        registryVo.setUsername("athena");
        registryVo.setPassword("athena");
        registryVo.setConfirmPassword("athena");
        userService.add(registryVo);
    }

    @Test
    public void testQueryByName() {
        User user = userService.queryByName("athena");
        Assert.assertNotNull("user不能为空", user);
    }

    @Test
    public void testSearch() {
        UserSearch userSearch = new UserSearch();
//        userSearch.setUsername("test");
        userSearch.setPageSize(2);
        userSearch.setCurrentPage(1);
        Page<User> page = userService.search(userSearch);
        Assert.assertNotNull("用户数据不为空", page);
    }

    @Test
    public void testDelete(){
        userService.delete(5);
    }

    @Test
    public void testUpdate(){
        RegistryVo registryVo = new RegistryVo();
        registryVo.setId(1);
        registryVo.setUsername("athena");
        registryVo.setPassword("athena1");
        registryVo.setConfirmPassword("athena");
        userService.update(registryVo);
    }

    @Test
    public void testGet(){
        User user = userService.get(1);
        Assert.assertNotNull("用户信息不能为空", user);
    }


}
