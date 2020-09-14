package com.network.management.agent.collector;

import com.network.management.domain.bo.DataBo;
import com.network.management.domain.bo.DeviceBo;

/**
 * 数据采集接口
 *
 * @author yyc
 * @date 2020/9/12 21:46
 */
public interface Collector {
    /**
     * 采集数据接口
     *
     * @param deviceBo {@link DeviceBo}
     * @return {@link DataBo}
     */
    DataBo<?> collect(DeviceBo deviceBo);
}
