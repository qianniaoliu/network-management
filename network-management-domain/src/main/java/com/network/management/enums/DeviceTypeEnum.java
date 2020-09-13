package com.network.management.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 设备类型枚举类
 * @author yyc
 * @date 2020/9/13 11:29
 */
@AllArgsConstructor
@Getter
public enum DeviceTypeEnum {
    FLASH_STATION(1, "flashStation"),
    WEB_STATION(2, "webStation"),
    OTHER_STATION(3, "otherDevice");

    private Integer type;
    private String typeKey;

    /**
     * 获取设备类型key
     * @param type
     * @return
     */
    public static String getTypeKey(Integer type){
        if(Objects.nonNull(type)){
            for(DeviceTypeEnum typeEnum : DeviceTypeEnum.values()){
                if(Objects.equals(type, typeEnum.getType())){
                    return typeEnum.getTypeKey();
                }
            }
        }
        return null;
    }
}
