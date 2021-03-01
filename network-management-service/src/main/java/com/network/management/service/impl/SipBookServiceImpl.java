package com.network.management.service.impl;

import com.network.management.common.exception.Assert;
import com.network.management.common.exception.IllegalParamException;
import com.network.management.domain.dao.SipBook;
import com.network.management.domain.vo.SipBookVo;
import com.network.management.mapper.SipBookMapper;
import com.network.management.service.SipBookService;
import com.network.management.service.converter.SipBookVoConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * sip服务实现类
 * @author yyc
 * @date 2021/2/28 12:40
 */
@Service
@Slf4j
public class SipBookServiceImpl implements SipBookService {
    @Autowired
    private SipBookMapper sipBookMapper;
    @Autowired
    private SipBookVoConverter sipBookVoConverter;

    @Override
    public void add(SipBookVo sipBookVo) {
        Assert.notNull(sipBookVo, "sipBook对象不能为空!");
        SipBook sipBook = sipBookMapper.querySipBookBySip(sipBookVo.getSip());
        if(Objects.nonNull(sipBook)){
            throw new IllegalParamException("sip已存在！");
        }
        SipBook sipBook2 = sipBookVoConverter.reverseConvert(sipBookVo);
        if(Objects.isNull(sipBook2)){
            throw new IllegalParamException("SipBookVoConverter转换后的SipBook为空！");
        }
        sipBookMapper.insert(sipBook2);
    }

    @Override
    public void update(SipBookVo sipBookVo) {
        Assert.notNull(sipBookVo, "sipBook对象不能为空!");
        SipBook sipBook = sipBookVoConverter.reverseConvert(sipBookVo);
        if(Objects.isNull(sipBook)){
            throw new IllegalParamException("SipBookVoConverter转换后的SipBook为空！");
        }
        sipBookMapper.updateByPrimaryKeySelective(sipBook);
    }

    @Override
    public void delete(Integer id) {
        Assert.notNull(id, "sipBook id不能为空!");
        sipBookMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<SipBookVo> queryAllSip() {
        return sipBookVoConverter.convertToList(sipBookMapper.queryAllSip());
    }

    @Override
    public List<SipBookVo> querySipByBookId(Integer bookId) {
        Assert.notNull(bookId, "通讯录id不能为空!");
        return sipBookVoConverter.convertToList(sipBookMapper.querySipByBookId(bookId));
    }
}
