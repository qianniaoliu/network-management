package com.network.management.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.network.management.common.DateUtils;
import com.network.management.common.constants.CommonConstants;
import com.network.management.common.httpclient.HttpClientUtils;
import com.network.management.domain.bo.LocomotiveAccessDataItemBo;
import com.network.management.domain.bo.LocomotiveStatisticsBo;
import com.network.management.domain.vo.LocomotiveAccessDataVo;
import com.network.management.domain.vo.LocomotiveData;
import com.network.management.domain.vo.LocomotiveRecordVo;
import com.network.management.domain.vo.LocomotiveStatisticsVo;
import com.network.management.service.LocomotiveRecordService;
import com.network.management.service.LocomotiveStatisticsService;
import com.network.management.service.converter.LocomotiveStatisticsVoConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Autowired
    private LocomotiveRecordService locomotiveRecordService;
    /**
     * 机车统计数量URL
     */
    private static final String LOCOMOTIVE_STATISTICS_NUMBER = "http://172.16.11.17/federal-api/api/alarm-data/dashboard/overview";
    private static final String LOCOMOTIVE_ACCESS_DATA_URL = "http://172.25.11.69:8099/get/locomotive/statistics";
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

    @Override
    public LocomotiveAccessDataVo queryLocomotiveData() {
        return getNewLocomotiveAccessDataVo();
    }

    private LocomotiveAccessDataVo getNewLocomotiveAccessDataVo() {
        List<LocomotiveRecordVo> allLocomotiveRecordVos = Lists.newArrayList();
        Integer currentPage = 1;
        Integer pageSize = 100;
        while (true) {
            List<LocomotiveRecordVo> locomotiveRecordVos = locomotiveRecordService.queryLocomotiveRecords(currentPage, pageSize);
            if (CollectionUtils.isEmpty(locomotiveRecordVos)) {
                break;
            }
            allLocomotiveRecordVos.addAll(locomotiveRecordVos);
            currentPage++;
        }
        allLocomotiveRecordVos.sort((o1, o2) -> o2.getOccurDate().compareTo(o1.getOccurDate()));

        return resolveLocomotiveAccessData(allLocomotiveRecordVos);
    }

    private LocomotiveAccessDataVo resolveLocomotiveAccessData(List<LocomotiveRecordVo> allLocomotiveRecordVos) {
        if (CollectionUtils.isEmpty(allLocomotiveRecordVos)) {
            return null;
        }
        /**
         * 通过南翼/北翼，进出场作为key，例子如下
         * 南翼进场=1
         * 南翼出场=2
         * 北翼进场=3
         * 北翼出场=4
         */
        Map<String, LocomotiveData> locomotiveDataMap = new HashMap<>();

        /**
         * key=南翼表示南翼进出场记录
         * key=北翼表示北翼进出场记录
         */
        Map<String, List<String>> particularsMap = new HashMap<>();
        Date nowDate = new Date();
        for (LocomotiveRecordVo locomotiveRecordVo : allLocomotiveRecordVos) {
            StringBuilder sb = new StringBuilder(DateUtils.formatDateString(locomotiveRecordVo.getOccurDate()))
                    .append(CommonConstants.COMMA_KEY)
                    .append(locomotiveRecordVo.getDirection())
                    .append(CommonConstants.COMMA_KEY)
                    .append("车辆类型：")
                    .append(locomotiveRecordVo.getVehicleType())
                    .append(CommonConstants.COMMA_KEY)
                    .append("车厢节数：")
                    .append(locomotiveRecordVo.getSectionNumber());
            particularsMap.putIfAbsent(locomotiveRecordVo.getLocation(), new ArrayList<>());
            List<String> particulars = particularsMap.get(locomotiveRecordVo.getLocation());
            particulars.add(sb.toString());

            locomotiveDataMap.putIfAbsent(locomotiveRecordVo.getLocation() + locomotiveRecordVo.getDirection(), new LocomotiveData());
            LocomotiveData locomotiveData = locomotiveDataMap.get(locomotiveRecordVo.getLocation() + locomotiveRecordVo.getDirection());
            if (StringUtils.isBlank(locomotiveData.getTitle())
                    || StringUtils.isBlank(locomotiveData.getType())) {
                locomotiveData.setTitle(locomotiveRecordVo.getLocation());
                locomotiveData.setType(locomotiveRecordVo.getDirection());
            }
            if (DateUtils.isToday(locomotiveRecordVo.getOccurDate())) {
                locomotiveData.setDayStatistics(locomotiveData.getDayStatistics() + locomotiveRecordVo.getSectionNumber());
            }
            if (DateUtils.isFirstThirdMonth(nowDate)
                    && DateUtils.isFirstThirdMonth(locomotiveRecordVo.getOccurDate())) {
                locomotiveData.setWeekStatistics(locomotiveData.getWeekStatistics() + locomotiveRecordVo.getSectionNumber());
            } else if (DateUtils.isMiddleThirdMonth(nowDate)
                    && DateUtils.isMiddleThirdMonth(locomotiveRecordVo.getOccurDate())) {
                locomotiveData.setWeekStatistics(locomotiveData.getWeekStatistics() + locomotiveRecordVo.getSectionNumber());
            } else if (DateUtils.isLastThirdMonth(nowDate)
                    && DateUtils.isLastThirdMonth(locomotiveRecordVo.getOccurDate())) {
                locomotiveData.setWeekStatistics(locomotiveData.getWeekStatistics() + locomotiveRecordVo.getSectionNumber());
            }
//            if (DateUtils.isCurrentWeek(locomotiveRecordVo.getOccurDate())) {
//                locomotiveData.setWeekStatistics(locomotiveData.getWeekStatistics() + locomotiveRecordVo.getSectionNumber());
//            }
            if (DateUtils.isCurrentMonth(locomotiveRecordVo.getOccurDate())) {
                locomotiveData.setMonthStatistics(locomotiveData.getMonthStatistics() + locomotiveRecordVo.getSectionNumber());
            }
        }
        LocomotiveAccessDataVo result = new LocomotiveAccessDataVo();
        List<LocomotiveData> locomotiveDatas = Lists.newArrayList(locomotiveDataMap.values());
        Collections.sort(locomotiveDatas);
        result.setLocomotiveDatas(locomotiveDatas);
        result.setSouthParticulars(particularsMap.get(CommonConstants.SOUTH_LOCOMOTIVE_KEY));
        result.setNorthParticulars(particularsMap.get(CommonConstants.NORTH_LOCOMOTIVE_KEY));
        return result;
    }

    private LocomotiveAccessDataVo getOldLocomotiveAccessDataVo() {
        List<String> originLocomotiveAccessData = getOriginLocomotiveAccessData(LOCOMOTIVE_ACCESS_DATA_URL);
        if (CollectionUtils.isNotEmpty(originLocomotiveAccessData)) {
            originLocomotiveAccessData.sort((o1, o2) -> {
                LocomotiveAccessDataItemBo bo1 = getLocomotiveAccessDataItemBo(o1);
                LocomotiveAccessDataItemBo bo2 = getLocomotiveAccessDataItemBo(o2);
                return DateUtils.parseDateString(bo2.getDate()).compareTo(DateUtils.parseDateString(bo1.getDate()));
            });
        }

        return resolveOriginLocomotiveAccessData(originLocomotiveAccessData);
    }

    /**
     * 将原始明细数据转换成标准业务模型
     *
     * @param originLocomotiveAccessData 原始明细数据
     * @return {@link LocomotiveAccessDataVo}
     */
    private LocomotiveAccessDataVo resolveOriginLocomotiveAccessData(List<String> originLocomotiveAccessData) {
        if (CollectionUtils.isEmpty(originLocomotiveAccessData)) {
            return null;
        }
        LocomotiveAccessDataVo result = new LocomotiveAccessDataVo();
        List<LocomotiveData> locomotiveDatas = new ArrayList<>();
        List<String> southParticulars = new ArrayList<>();
        southParticulars.addAll(originLocomotiveAccessData);
        result.setLocomotiveDatas(locomotiveDatas);
        result.setSouthParticulars(southParticulars);
        // 北翼入场数据（天）
        int southEnterDayData = 0;
        // 北翼入场数据（周）
        int southEnterWeekData = 0;
        // 北翼入场数据（月）
        int southEnterMonthData = 0;

        // 北翼出场数据（天）
        int southOutDayData = 0;
        // 北翼出场数据（周）
        int southOutWeekData = 0;
        // 北翼出场数据（月）
        int southOutMonthData = 0;
        for (String originData : originLocomotiveAccessData) {
            LocomotiveAccessDataItemBo locomotiveAccessDataItemBo = getLocomotiveAccessDataItemBo(originData);
            if (Objects.isNull(locomotiveAccessDataItemBo)) {
                continue;
            }
            if (StringUtils.equals(locomotiveAccessDataItemBo.getType(), CommonConstants.ENTRANCE_KEY)) {
                Date date = DateUtils.parseDateString(locomotiveAccessDataItemBo.getDate());
                if (DateUtils.isToday(date)) {
                    southEnterDayData++;
                }
                if (DateUtils.isCurrentWeek(date)) {
                    southEnterWeekData++;
                }
                if (DateUtils.isCurrentMonth(date)) {
                    southEnterMonthData++;
                }

            } else if (StringUtils.equals(locomotiveAccessDataItemBo.getType(), CommonConstants.OUT_KEY)) {
                Date date = DateUtils.parseDateString(locomotiveAccessDataItemBo.getDate());
                if (DateUtils.isToday(date)) {
                    southOutDayData++;
                }
                if (DateUtils.isCurrentWeek(date)) {
                    southOutWeekData++;
                }
                if (DateUtils.isCurrentMonth(date)) {
                    southOutMonthData++;
                }
            }
        }
        // 南翼进场数据
        LocomotiveData southEnterData = new LocomotiveData();
        southEnterData.setTitle(CommonConstants.SOUTH_LOCOMOTIVE_KEY);
        southEnterData.setType(CommonConstants.ENTRANCE_KEY);
        southEnterData.setDayStatistics(southEnterDayData);
        southEnterData.setWeekStatistics(southEnterWeekData);
        southEnterData.setMonthStatistics(southEnterMonthData);
        locomotiveDatas.add(southEnterData);

        // 南翼出场数据
        LocomotiveData southOutData = new LocomotiveData();
        southOutData.setTitle(CommonConstants.SOUTH_LOCOMOTIVE_KEY);
        southOutData.setType(CommonConstants.OUT_KEY);
        southOutData.setDayStatistics(southOutDayData);
        southOutData.setWeekStatistics(southOutWeekData);
        southOutData.setMonthStatistics(southOutMonthData);
        locomotiveDatas.add(southOutData);

        // 北翼进场数据
        LocomotiveData northEnterData = new LocomotiveData();
        northEnterData.setTitle(CommonConstants.NORTH_LOCOMOTIVE_KEY);
        northEnterData.setType(CommonConstants.ENTRANCE_KEY);
        northEnterData.setDayStatistics(0);
        northEnterData.setWeekStatistics(0);
        northEnterData.setMonthStatistics(0);
        locomotiveDatas.add(northEnterData);

        // 北翼出场数据
        LocomotiveData northOutData = new LocomotiveData();
        northOutData.setTitle(CommonConstants.NORTH_LOCOMOTIVE_KEY);
        northOutData.setType(CommonConstants.OUT_KEY);
        northOutData.setDayStatistics(0);
        northOutData.setWeekStatistics(0);
        northOutData.setMonthStatistics(0);
        locomotiveDatas.add(northOutData);
        return result;
    }

    /**
     * 将机车原始数据转换成业务模型
     *
     * @param originData 机车原始数据
     * @return 业务模型
     */
    private static LocomotiveAccessDataItemBo getLocomotiveAccessDataItemBo(String originData) {
        if (StringUtils.isBlank(originData)) {
            return null;
        }
        String[] originDatArray = StringUtils.split(originData, CommonConstants.COMMA_KEY);
        // 标准数据长度应该为2
        if (originDatArray.length == 2) {
            LocomotiveAccessDataItemBo result = new LocomotiveAccessDataItemBo();
            result.setDate(originDatArray[0]);
            result.setType(originDatArray[1]);
            return result;
        }
        return null;
    }


    /**
     * 通过url获取远端机车数据
     *
     * @param locomotiveAccessDataUrl url
     * @return 机车数据明细
     */
    private List<String> getOriginLocomotiveAccessData(String locomotiveAccessDataUrl) {
        try {
            String originResult = HttpClientUtils.doGet(locomotiveAccessDataUrl, Integer.parseInt(timeOut));
            if (StringUtils.isBlank(originResult)) {
                return null;
            }
            return JSONArray.parseArray(originResult, String.class);
        } catch (Exception e) {
            log.error("query Locomotive Access Data error.", e);
        }
        return null;
    }

    /**
     * 获取LocomotiveStatisticsVo对象
     *
     * @param locomotiveNumberData 查询机车返回值
     * @return {@link LocomotiveStatisticsVo}
     */
    private LocomotiveStatisticsVo getLocomotiveStatisticsVo(String locomotiveNumberData) {
        if (StringUtils.isNotBlank(locomotiveNumberData)) {
            LocomotiveStatisticsBo locomotiveStatisticsBo = JSON.parseObject(locomotiveNumberData, LocomotiveStatisticsBo.class);
            log.info("getLocomotiveStatisticsVo LocomotiveStatisticsBo:{}", JSON.toJSONString(locomotiveStatisticsBo));
            if (Objects.nonNull(locomotiveStatisticsBo)) {
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
