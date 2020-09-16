package com.network.management.mapper;

import com.network.management.domain.dao.BordInformation;

import java.util.List;

/**
 * 巷道图基本信息mapper
 *
 * @author yusheng
 */
public interface BordInformationMapper {

    /**
     * 新增巷道图基本信息
     * @param bordInformation 插入信息
     * @return 主键id
     */
    Integer insert(BordInformation bordInformation);

    /**
     * 根据主键修改巷道图基本信息
     * @param bordInformation 修改信息
     */
    void updateByKey(BordInformation bordInformation);

    /**
     * 获取巷道图基本信息
     * @param bordId 巷道图id
     * @return 巷道图基本信息
     */
    BordInformation selectByPrimaryKey(Integer bordId);

    /**
     * 获取所有巷道图信息
     * @return 巷道图列表
     */
    List<BordInformation> selectAll();
}
