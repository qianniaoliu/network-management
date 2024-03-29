package com.network.management.agent.collector.impl;

import com.network.management.agent.annotation.DeviceCollectorType;
import com.network.management.agent.collector.Collector;
import com.network.management.domain.bo.DataBo;
import com.network.management.domain.bo.DeviceBo;
import com.network.management.domain.bo.OtherDeviceStatusBo;
import com.network.management.domain.enums.YnEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;
import java.net.InetAddress;

/**
 * 其他设备状态采集器
 *
 * @author yyc
 * @date 2020/9/12 22:05
 */
@Component
@DeviceCollectorType("otherDevice")
@Slf4j
public class OtherDeviceCollector implements Collector {
    /**
     * ping超时时间
     */
    private static final int TIME_OUT = 5000;

    @Override
    public DataBo<?> collect(DeviceBo deviceBo) {
        Assert.notNull(deviceBo, "其他设备基本信息不能为空.");
        Assert.notNull(deviceBo.getIp(), "其他设备ip信息不能为空.");
        Assert.notNull(deviceBo.getEquipmentType(), "设备类型信息不能为空.");
        boolean status = false;
        try {
            status = InetAddress.getByName(deviceBo.getIp()).isReachable(TIME_OUT);
        } catch (IOException e) {
            log.error(String.format("其他设备基本ip:%s 网络连通失败.", deviceBo.getIp()), e);
        }
        DataBo<OtherDeviceStatusBo> dataBo = new DataBo<OtherDeviceStatusBo>();
        dataBo.setIp(deviceBo.getIp());
        dataBo.setType(deviceBo.getEquipmentType());
        OtherDeviceStatusBo deviceStatusBo = new OtherDeviceStatusBo();
        deviceStatusBo.setStatus(status ? YnEnum.YES.getCode() : YnEnum.NO.getCode());
        dataBo.setDataObj(deviceStatusBo);
        return dataBo;
    }
}
