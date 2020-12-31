package com.network.management.service.impl;

import com.alibaba.fastjson.JSON;
import com.network.management.common.httpclient.HttpClientUtils;
import com.network.management.domain.bo.LocomotiveStatisticsBo;
import com.network.management.domain.vo.LocomotiveStatisticsVo;
import com.network.management.service.LocomotiveStatisticsService;
import com.network.management.service.converter.LocomotiveStatisticsVoConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 机车数量统计服务实现类
 *
 * @author yyc
 * @date 2020/12/31 18:47
 */
@Service
@Slf4j
public class LocomotiveStatisticsServiceImpl implements LocomotiveStatisticsService {
    @Autowired
    private LocomotiveStatisticsVoConverter locomotiveStatisticsVoConverter;
    /**
     * 机车统计数量URL
     */
    private static final String LOCOMOTIVE_STATISTICS_NUMBER = "http://172.16.11.17/federal-api/api/alarm-date/dashboard/overview";
    /**
     * 查询机车统计返回结果key
     */
    private static final String LOCOMOTIVE_DATA = "data";
    private static final String LOCOMOTIVE_NUMBER_TOTAL = "total";
    private static final String LOCOMOTIVE_NUMBER_YESTERDAY = "yesterday";
    private static final String LOCOMOTIVE_NUMBER_TODAY = "today";
    /**
     * 超时时间
     */
    @Value("${locomotive.time.out:5000}")
    private String timeOut;

    @Override
    public LocomotiveStatisticsVo queryLocomotiveStatisticsNumbers() {
        String locomotiveNumberData = queryLocomotiveNumbers(LOCOMOTIVE_STATISTICS_NUMBER);
        log.info("query locomotive Number Data:{}", locomotiveNumberData);
        return getLocomotiveStatisticsVo(locomotiveNumberData);
    }

    /**
     * 获取LocomotiveStatisticsVo对象
     * @param locomotiveNumberData 查询机车返回值
     * @return {@link LocomotiveStatisticsVo}
     */
    private LocomotiveStatisticsVo getLocomotiveStatisticsVo(String locomotiveNumberData) {
        if (StringUtils.isNotBlank(locomotiveNumberData)) {
            LocomotiveStatisticsBo locomotiveStatisticsBo = JSON.parseObject(locomotiveNumberData, LocomotiveStatisticsBo.class);
            log.info("getLocomotiveStatisticsVo LocomotiveStatisticsBo:{}", JSON.toJSONString(locomotiveStatisticsBo));
            if(Objects.nonNull(locomotiveStatisticsBo)){
                return locomotiveStatisticsVoConverter.convert(locomotiveStatisticsBo.getData());
            }
        }
        return null;
    }


    /**
     * 查询机车统计数量URL
     *
     * @param url 机车统计数量URL
     */
    private String queryLocomotiveNumbers(String url) {
        try {
            return HttpClientUtils.doGet(url, Integer.parseInt(timeOut));
        } catch (Exception e) {
            log.error("query Locomotive Numbers error.", e);
        }
        return null;
    }
}
