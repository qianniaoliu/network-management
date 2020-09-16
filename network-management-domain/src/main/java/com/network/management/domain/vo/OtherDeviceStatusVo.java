package com.network.management.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 获取其他设备状态数据
 * @author yyc
 * @date 2020/9/15 20:48
 */
@Data
@AllArgsConstructor
public class OtherDeviceStatusVo{
    /**
     * ping连接状态 {@link com.network.management.domain.enums.YnEnum}
     */
    private Integer status;
}
