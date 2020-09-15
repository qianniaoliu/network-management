package com.network.management.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * flash界面基站状态枚举类
 * @author yyc
 * @date 2020/9/15 17:29
 */
@AllArgsConstructor
@Getter
public enum FlashStationStatusEnum {
    FLASH_STATUS_OK("OK"),
    FLASH_STATUS_OFF("OFF"),
    FLASH_STATUS_FAIL("FAIL"),
    FLASH_STATUS_UP("UP");

    private String status;
}
