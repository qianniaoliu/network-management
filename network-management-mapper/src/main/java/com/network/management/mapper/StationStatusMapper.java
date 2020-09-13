package com.network.management.mapper;

import com.network.management.dao.StationStatus;

import java.util.List;
import java.util.Map;

/**
 * 基站状态数据mapper
 * @author yyc
 * @date 2020/9/13 16:55
 */
public interface StationStatusMapper {

    Integer insert(StationStatus stationStatus);

    StationStatus getLatestByIp(String ip);

    List<StationStatus> getByConditions(Map<String, String> conditions);

}
