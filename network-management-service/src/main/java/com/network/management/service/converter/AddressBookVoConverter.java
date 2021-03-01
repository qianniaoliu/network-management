package com.network.management.service.converter;

import com.network.management.common.convert.Converter;
import com.network.management.domain.dao.AddressBook;
import com.network.management.domain.enums.YnEnum;
import com.network.management.domain.vo.AddressBookVo;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * AddressBookVo对象装换类
 * @author yyc
 * @date 2021/3/1 15:33
 */
@Component
public class AddressBookVoConverter implements Converter<AddressBook, AddressBookVo> {
    @Override
    public AddressBookVo convert(AddressBook addressBook) {
        if(Objects.nonNull(addressBook)){
            AddressBookVo addressBookVo = new AddressBookVo();
            addressBookVo.setId(addressBook.getId());
            addressBookVo.setDepartmentId(addressBook.getDepartmentId());
            addressBookVo.setAddressBookName(addressBook.getAddressBookName());
            return addressBookVo;
        }
        return null;
    }

    @Override
    public AddressBook reverseConvert(AddressBookVo addressBookVo) {
        if(Objects.nonNull(addressBookVo)){
            AddressBook addressBook = new AddressBook();
            addressBook.setId(addressBookVo.getId());
            addressBook.setAddressBookName(addressBookVo.getAddressBookName());
            addressBook.setDepartmentId(addressBookVo.getDepartmentId());
            if(Objects.isNull(addressBookVo.getId())){
                addressBook.setCreated(new Date());
                addressBook.setYn(YnEnum.YES.getCode());
            }
            addressBook.setModified(new Date());
            return addressBook;
        }
        return null;
    }
}
