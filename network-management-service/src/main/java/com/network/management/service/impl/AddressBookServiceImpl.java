package com.network.management.service.impl;

import com.network.management.common.exception.Assert;
import com.network.management.common.exception.IllegalParamException;
import com.network.management.domain.dao.AddressBook;
import com.network.management.domain.vo.AddressBookVo;
import com.network.management.mapper.AddressBookMapper;
import com.network.management.service.AddressBookService;
import com.network.management.service.converter.AddressBookVoConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 通讯录服务实现类
 * @author yyc
 * @date 2021/2/28 12:40
 */
@Service
@Slf4j
public class AddressBookServiceImpl implements AddressBookService {
    @Autowired
    private AddressBookMapper addressBookMapper;
    @Autowired
    private AddressBookVoConverter addressBookVoConverter;
    @Override
    public void add(AddressBookVo addressBookVo) {
        Assert.notNull(addressBookVo, "通讯录对象不能为空!");
        List<AddressBook> addressBooks = addressBookMapper.queryAllAddressBooks();
        ListUtils.emptyIfNull(addressBooks)
                .stream()
                .filter(Objects::nonNull)
                .forEach(book -> {
                    if(book.getAddressBookName().equals(addressBookVo.getAddressBookName())
                            || book.getDepartmentId().equals(addressBookVo.getDepartmentId())){
                        throw new IllegalParamException("通讯录已存在!");
                    }
                });
        AddressBook addressBook = addressBookVoConverter.reverseConvert(addressBookVo);
        if(Objects.isNull(addressBook)){
            throw new IllegalParamException("AddressBookVoConverter转换后的通讯录为空!");
        }
        addressBookMapper.insert(addressBook);
    }

    @Override
    public void update(AddressBookVo addressBookVo) {
        Assert.notNull(addressBookVo, "通讯录对象不能为空!");
        AddressBook addressBook = addressBookVoConverter.reverseConvert(addressBookVo);
        if(Objects.isNull(addressBook)){
            throw new IllegalParamException("AddressBookVoConverter转换后的通讯录为空!");
        }
        addressBookMapper.updateByPrimaryKeySelective(addressBook);
    }

    @Override
    public void delete(Integer id) {
        Assert.notNull(id, "通讯录id不能为空!");
        addressBookMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<AddressBookVo> queryAllAddressBook() {
        return addressBookVoConverter.convertToList(addressBookMapper.queryAllAddressBooks());
    }

    @Override
    public AddressBookVo queryAddressBookByDepartmentId(Integer departmentId) {
        return addressBookVoConverter.convert(addressBookMapper.queryAddressBookByDepartmentId(departmentId));
    }
}
