package com.network.management.service;

import com.network.management.domain.vo.SipBookVo;
import com.network.management.service.config.TestConfig;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

/**
 * 通讯录账号服务测试类
 * @author yyc
 * @date 2021/03/01 15:30
 */
@SpringBootTest(classes = TestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(value = {"classpath:application-test.properties"})
public class SipBookServiceTests {

    @Autowired
    private SipBookService sipBookService;

    @Test
    public void testAdd() {
        SipBookVo sipBookVo = new SipBookVo();
        sipBookVo.setSip(600700);
        sipBookVo.setSipName("老王");
        sipBookVo.setAddressBookId(1);
        sipBookService.add(sipBookVo);
    }

    @Test
    public void testUpdate(){
        SipBookVo sipBookVo = new SipBookVo();
        sipBookVo.setSip(600007);
        sipBookVo.setSipName("老王001");
        sipBookVo.setAddressBookId(2);
        sipBookVo.setId(1);
        sipBookService.update(sipBookVo);
    }

    @Test
    public void testQueryByBookId(){
        List<SipBookVo> sipBookVos = sipBookService.querySipByBookId(2);
        Assert.assertNotNull("通讯录账号数据不为空", sipBookVos);
    }

    @Test
    public void testQueryAll(){
        List<SipBookVo> sipBookVos = sipBookService.queryAllSip();
        Assert.assertNotNull("通讯录账号数据不为空", sipBookVos);
    }

    @Test
    public void testDelete(){
        sipBookService.delete(1);
    }
}
