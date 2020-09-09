package com.network.management.service.impl;

import com.network.management.BordInformation;
import com.network.management.mapper.BordInformationMapper;
import com.network.management.service.BordInformationService;
import com.network.management.vo.BordInformationAggregation;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 巷道图服务实现
 *
 * @author yusheng
 */
@Service
public class BordInformationServiceImpl implements BordInformationService {

    @Autowired
    private BordInformationMapper bordInformationMapper;


    @Override
    public void add(BordInformation bordInformation) {
        Assert.notNull(bordInformation, "bordInformation 对象不能为null");
        bordInformationMapper.insert(bordInformation);
    }

    @Override
    public void update(BordInformation bordInformation) {
        Assert.notNull(bordInformation, "bordInformation 对象不能为null");
        bordInformationMapper.updateByKey(bordInformation);
    }

    @Override
    public void update(BordInformationAggregation data) {

    }

    @Override
    public BordInformationAggregation get(Integer bordInformationId) {
        return null;
    }
}
