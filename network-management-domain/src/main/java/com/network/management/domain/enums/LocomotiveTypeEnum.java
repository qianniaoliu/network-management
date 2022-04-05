package com.network.management.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@AllArgsConstructor
@Getter
public enum LocomotiveTypeEnum {
    CORE_QUERY("1","数据来源:核心网"),
    DB_QUERY("2","数据来源:数据库"),
    NEW_CORE_QUERY("3","数据来源:新核心网");

    private String type;
    private String desc;

    /**
     * 根据机车数据来源类型获取机车枚举
     * @param type 机车数据来源类型
     * @return 机车枚举类 {@link LocomotiveTypeEnum}
     */
    public static LocomotiveTypeEnum getType(String type){
        if(StringUtils.isEmpty(type)){
            return null;
        }
        for(LocomotiveTypeEnum typeEnum : values()){
            if(typeEnum.getType().equals(type)){
                return typeEnum;
            }
        }
        return null;
    }
}
