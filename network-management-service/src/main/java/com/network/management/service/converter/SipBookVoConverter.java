package com.network.management.service.converter;

import com.network.management.common.convert.Converter;
import com.network.management.domain.dao.SipBook;
import com.network.management.domain.enums.YnEnum;
import com.network.management.domain.vo.SipBookVo;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * SipBook对象转换类
 * @author yyc
 * @date 2021/3/1 15:12
 */
@Component
public class SipBookVoConverter implements Converter<SipBook, SipBookVo> {
    @Override
    public SipBookVo convert(SipBook sipBook) {
        if(Objects.nonNull(sipBook)){
            SipBookVo sipBookVo = new SipBookVo();
            sipBookVo.setId(sipBook.getId());
            sipBookVo.setAddressBookId(sipBook.getAddressBookId());
            sipBookVo.setSip(sipBook.getSip());
            sipBookVo.setSipName(sipBook.getSipName());
            return sipBookVo;
        }
        return null;
    }

    @Override
    public SipBook reverseConvert(SipBookVo sipBookVo) {
        if(Objects.nonNull(sipBookVo)){
            SipBook sipBook = new SipBook();
            sipBook.setAddressBookId(sipBookVo.getAddressBookId());
            sipBook.setId(sipBookVo.getId());
            sipBook.setSip(sipBookVo.getSip());
            sipBook.setSipName(sipBookVo.getSipName());
            sipBook.setModified(new Date());
            if(Objects.isNull(sipBookVo.getId())){
                sipBook.setYn(YnEnum.YES.getCode());
                sipBook.setCreated(new Date());
            }
            return sipBook;
        }
        return null;
    }
}
