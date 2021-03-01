package com.network.management.service;

import com.network.management.domain.vo.AddressBookVo;
import com.network.management.service.config.TestConfig;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

/**
 * 通讯录服务测试类
 * @author yyc
 * @date 2021/03/01 15:30
 */
@SpringBootTest(classes = TestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(value = {"classpath:application-test.properties"})
public class AddressBookServiceTests {

    @Autowired
    private AddressBookService addressBookService;

    @Test
    public void testAdd() {
        AddressBookVo addressBookVo = new AddressBookVo();
        addressBookVo.setAddressBookName("通讯录1");
        addressBookVo.setDepartmentId(1);
        addressBookService.add(addressBookVo);
    }

    @Test
    public void testUpdate(){
        AddressBookVo addressBookVo = new AddressBookVo();
        addressBookVo.setAddressBookName("通讯录2");
        addressBookVo.setDepartmentId(3);
        addressBookVo.setId(1);
        addressBookService.update(addressBookVo);
    }

    @Test
    public void testQueryAddressBookByDepartmentId(){
        AddressBookVo addressBookVo = addressBookService.queryAddressBookByDepartmentId(3);
        Assert.assertNotNull("通讯录数据不为空", addressBookVo);
    }

    @Test
    public void testQueryAll(){
        List<AddressBookVo> addressBookVos = addressBookService.queryAllAddressBook();
        Assert.assertNotNull("通讯录数据不为空", addressBookVos);
    }

    @Test
    public void testDelete(){
        addressBookService.delete(1);
    }
}
