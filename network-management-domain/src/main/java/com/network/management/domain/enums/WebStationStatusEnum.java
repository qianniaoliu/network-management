package com.network.management.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * web界面基站状态枚举类
 * @author yyc
 * @date 2020/9/15 17:29
 */
@AllArgsConstructor
@Getter
public enum WebStationStatusEnum {
    WEB_STATUS_1("1"),
    WEB_STATUS_0("0"),
    WEB_STATUS_UP("up"),
    WEB_STATUS_ENABLED("enabled");

    private String status;
}
