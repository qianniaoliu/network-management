package com.network.management.service;

import com.network.management.domain.vo.AuthorityVo;
import com.network.management.service.config.TestConfig;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

/**
 * 授权明细服务测试类
 *
 * @author yyc
 * @date 2021/03/25 15:30
 */
@SpringBootTest(classes = TestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(value = {"classpath:application-test.properties"})
public class AuthorityServiceTests {
    @Autowired
    private AuthorityService authorityService;

    @Test
    public void testAdd() {
        AuthorityVo authorityVo = new AuthorityVo();
        authorityVo.setDesc("机车管理");
        authorityService.add(authorityVo);
    }

    @Test
    public void testUpdate() {
        AuthorityVo authorityVo = new AuthorityVo();
        authorityVo.setDesc("机车管理2");
        authorityVo.setId(1);
        authorityService.update(authorityVo);
    }

    @Test
    public void testQueryAuthorityVoById() {
        AuthorityVo authorityVo = authorityService.queryById(1);
        Assert.assertNotNull("授权明细数据不为空", authorityVo);
    }

    @Test
    public void testQueryAll() {
        List<AuthorityVo> authorityVos = authorityService.queryAllAuthorityVos();
        Assert.assertNotNull("授权明细数据不为空", authorityVos);
    }

    @Test
    public void testDelete() {
        authorityService.delete(1);
    }
}
