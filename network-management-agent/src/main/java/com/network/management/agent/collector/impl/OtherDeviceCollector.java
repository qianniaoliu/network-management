package com.network.management.agent.collector.impl;

import com.network.management.agent.collector.Collector;
import com.network.management.bo.DataBo;
import com.network.management.bo.DeviceBo;
import com.network.management.bo.DeviceStatusBo;
import com.network.management.common.exception.BizException;
import com.network.management.common.exception.ErrorCodeEnum;
import com.network.management.enums.YnEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;
import java.net.InetAddress;

/**
 * 其他设备状态采集器
 * @author yyc
 * @date 2020/9/12 22:05
 */
@Component
@Slf4j
public class OtherDeviceCollector implements Collector{
    /**
     * ping超时时间
     */
    private static final int TIME_OUT = 3000;
    @Override
    public DataBo<?> collect(DeviceBo deviceBo) {
        Assert.notNull(deviceBo, "其他设备基本信息不能为空.");
        Assert.notNull(deviceBo.getIp(), "其他设备ip信息不能为空.");
        Assert.notNull(deviceBo.getEquipmentType(), "设备类型信息不能为空.");
        try {
            boolean status = InetAddress.getByName(deviceBo.getIp()).isReachable(TIME_OUT);
            DataBo<DeviceStatusBo> dataBo = new DataBo<DeviceStatusBo>();
            dataBo.setIp(deviceBo.getIp());
            dataBo.setType(deviceBo.getEquipmentType());
            DeviceStatusBo deviceStatusBo = new DeviceStatusBo();
            deviceStatusBo.setPingStatus(status ? YnEnum.YES.getCode() : YnEnum.NO.getCode());
            dataBo.setDataObj(deviceStatusBo);
            return dataBo;
        } catch (IOException e) {
            log.error(String.format("其他设备基本ip:%s 网络连通失败.", deviceBo.getIp()), e);
            throw new BizException(ErrorCodeEnum.PING_ERROR);
        }
    }
}
