package com.network.management.service;

import com.network.management.domain.vo.LocomotiveStatisticsVo;

/**
 * 机车数量统计服务
 * @author yyc
 * @date 2020/12/31 18:41
 */
public interface LocomotiveStatisticsService {
    /**
     * 查询机车统计数量
     * @return
     */
    LocomotiveStatisticsVo queryLocomotiveStatisticsNumbers();
}
