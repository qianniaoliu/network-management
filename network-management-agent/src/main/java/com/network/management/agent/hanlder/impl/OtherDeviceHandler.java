package com.network.management.agent.hanlder.impl;

import com.alibaba.fastjson.JSON;
import com.network.management.agent.convert.OtherDeviceStatusConverter;
import com.network.management.agent.hanlder.DataHandler;
import com.network.management.domain.bo.DataBo;
import com.network.management.domain.dao.OtherDeviceStatus;
import com.network.management.mapper.OtherDeviceStatusMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 其他设备状态数据处理类(入库)
 *
 * @author yyc
 * @date 2020/9/12 22:46
 */
@Component
@Slf4j
public class OtherDeviceHandler implements DataHandler {
    @Autowired
    private OtherDeviceStatusMapper otherDeviceStatusMapper;
    @Autowired
    private OtherDeviceStatusConverter converter;

    @Override
    public void handle(DataBo<?> dataBo) {
        OtherDeviceStatus otherDeviceStatus = converter.convert(dataBo);
        if(Objects.nonNull(otherDeviceStatus)){
            otherDeviceStatusMapper.insert(otherDeviceStatus);
        }else {
            log.error("OtherDeviceHandler OtherDeviceStatus is null:{}", JSON.toJSONString(dataBo));
        }
    }
}
