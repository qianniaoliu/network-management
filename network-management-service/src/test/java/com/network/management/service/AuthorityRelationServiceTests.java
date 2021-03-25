package com.network.management.service;

import com.network.management.domain.vo.AuthorityRelationVo;
import com.network.management.service.config.TestConfig;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户授权明细服务测试类
 *
 * @author yyc
 * @date 2021/03/25 15:30
 */
@SpringBootTest(classes = TestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(value = {"classpath:application-test.properties"})
public class AuthorityRelationServiceTests {
    @Autowired
    private AuthorityRelationService authorityRelationService;

    @Test
    public void testAdd() {
        List<AuthorityRelationVo> authorityRelationVos = new ArrayList<>();
        AuthorityRelationVo authorityRelationVo = new AuthorityRelationVo();
        authorityRelationVo.setUserId(1);
        authorityRelationVo.setAuthorityId(1);
        AuthorityRelationVo authorityRelationVo1 = new AuthorityRelationVo();
        authorityRelationVo1.setUserId(2);
        authorityRelationVo1.setAuthorityId(2);
        authorityRelationVos.add(authorityRelationVo);
        authorityRelationVos.add(authorityRelationVo1);
        authorityRelationService.add(authorityRelationVos);
    }

    @Test
    public void testUpdate() {
        List<AuthorityRelationVo> authorityRelationVos = new ArrayList<>();
        AuthorityRelationVo authorityRelationVo = new AuthorityRelationVo();
        authorityRelationVo.setUserId(3);
        authorityRelationVo.setAuthorityId(13);
        authorityRelationVos.add(authorityRelationVo);
        authorityRelationService.updateAuthorityRelationVos(authorityRelationVos);
    }

    @Test
    public void testQueryByUserId() {
        List<AuthorityRelationVo> authorityRelationVos = authorityRelationService.queryAllAuthorityRelationVos(3);
        Assert.assertNotNull("用户授权明细数据不为空", authorityRelationVos);
    }

    @Test
    public void testDeleteByUserId() {
        authorityRelationService.deleteByUserId(3);
    }
}
