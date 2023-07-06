package com.network.management.domain.enums;

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
    FLASH_STATION(1, "flashStation", "超讯基站"),
    WEB_STATION(2, "webStation", "瑞斯康达基站"),
    OTHER_STATION(3, "otherDevice", "其他设备"),
    OTHER_STATION_TEMPA(4, "otherDevice", "其他设备"),
    OTHER_STATION_TEMPB(5, "otherDevice", "其他设备"),
    OTHER_STATION_TEMPC(6, "otherDevice", "其他设备");

    private Integer type;
    private String typeKey;
    private String desc;

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
