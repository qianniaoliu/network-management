package com.network.management.service;

import com.network.management.domain.dao.BordInformation;
import com.network.management.domain.vo.BordInformationAggregation;

import java.util.List;

/**
 * 巷道信息服务
 *
 * @author yusheng
 */
public interface BordInformationService {

    /**
     * 保存单个巷道图基本信息
     * @param bordInformation
     */
    void save(BordInformation bordInformation);

    /**
     * 完整保存整个巷道图信息
     * @param data 巷道图聚合信息
     */
    void updateAll(BordInformationAggregation data);


    /**
     * 获取整个巷道图信息
     * @return 巷道图聚合信息
     * @see BordInformationAggregation
     */
    BordInformationAggregation getAll();

    /**
     * 获取所有巷道图信息
     * @return 巷道图列表
     */
    List<BordInformation> selectAll();

}
