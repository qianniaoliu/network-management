package com.network.management.agent.Monitor;

import com.network.management.domain.bo.DeviceBo;

/**
 * 数据监控接口
 *
 * @author yyc
 * @date 2020/9/12 22:31
 */
public interface Monitor {
    /**
     * 设备状态监控接口
     * @param deviceBo {@link DeviceBo}
     */
    void monitoring(DeviceBo deviceBo);
}
