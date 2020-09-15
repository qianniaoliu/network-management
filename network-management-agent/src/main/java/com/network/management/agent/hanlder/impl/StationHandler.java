package com.network.management.agent.hanlder.impl;

import com.alibaba.fastjson.JSON;
import com.network.management.agent.convert.StationStatusConverter;
import com.network.management.agent.hanlder.DataHandler;
import com.network.management.domain.bo.DataBo;
import com.network.management.domain.dao.StationStatus;
import com.network.management.mapper.StationStatusMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * web界面基站状态数据处理类(入库)
 *
 * @author yyc
 * @date 2020/9/12 22:48
 */
@Component
@Slf4j
public class StationHandler implements DataHandler {
    @Autowired
    private StationStatusConverter converter;
    @Autowired
    private StationStatusMapper stationStatusMapper;

    @Override
    public void handle(DataBo<?> dataBo) {
        StationStatus stationStatus = converter.convert(dataBo);
        if (Objects.nonNull(stationStatus)) {
            stationStatusMapper.insert(stationStatus);
        } else {
            log.error("StationHandler StationStatus is null:{}", JSON.toJSONString(dataBo));
        }
    }
}
