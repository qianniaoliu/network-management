package com.network.management.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 机车连接状态
 * @author yyc
 * @date 2020/10/15 22:45
 */
@AllArgsConstructor
@Getter
public enum LocomotiveConnectEnum {
    DETACHED("detached", "未连接状态"),
    CONNECTED("connected", "连接状态"),
    IDLE("idle", "空闲状态");

    private String status;
    private String desc;

    public static Boolean isConnect(String status){
        return StringUtils.isNotEmpty(status) && (status.startsWith(LocomotiveConnectEnum.CONNECTED.getStatus())
                || status.startsWith(LocomotiveConnectEnum.IDLE.getStatus()));
    }
}
