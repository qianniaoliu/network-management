package com.network.management.service;

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
@SpringBootTest(classes = TestConfig.class, webEnvironment= SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(value = {"classpath:application-test.properties"})
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Test
    public void testRegistry(){
        RegistryVo registryVo = new RegistryVo();
        registryVo.setUserName("athena");
        registryVo.setPassword("athena");
        registryVo.setConfirmPassword("athena");
        userService.add(registryVo);
    }

    @Test
    public void testQueryByName(){
        User user = userService.queryByName("athena");
        Assert.assertNotNull("user不能为空", user);
    }

}
