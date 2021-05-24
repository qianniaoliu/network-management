package com.network.management.service.impl;

import com.network.management.domain.dao.ShareMessage;
import com.network.management.domain.search.ShareMessageSearch;
import com.network.management.domain.vo.ShareMessageVo;
import com.network.management.mapper.ShareMessageMapper;
import com.network.management.service.ShareMessageService;
import com.network.management.service.converter.ShareMessageVoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yusheng
 */
@Service
public class ShareMessageServiceImpl implements ShareMessageService {

    @Autowired
    private ShareMessageVoConverter converter;

    @Autowired
    private ShareMessageMapper shareMessageMapper;

    @Override
    public Integer add(ShareMessageVo shareMessageVo) {
        ShareMessage shareMessage = converter.reverseConvert(shareMessageVo);
        return shareMessageMapper.insert(shareMessage);
    }

    @Override
    public List<ShareMessageVo> search(ShareMessageSearch shareMessageSearch) {
        return converter.convertToList(shareMessageMapper.search(shareMessageSearch));
    }
}
