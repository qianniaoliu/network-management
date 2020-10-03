package com.network.management.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yusheng
 */
@Getter
@AllArgsConstructor
public enum TimepieceEnum {

    /**
     * 失步
     */
    ZERO(0, "失步"),

    /**
     * 同步中
     */
    ONE(1, "同步中"),

    /**
     * 已同步
     */
    TWO(2, "已同步"),

    /**
     * 备用时钟检测中
     */
    THREE(3, "备用时钟检测中"),

    /**
     * 保持
     */
    FOUR(4, "保持"),

    /**
     * 重同步中
     */
    FIVE(5, "重同步中");

    private Integer key;
    private String desc;

    public static TimepieceEnum getByKey(Integer key){
        for(TimepieceEnum connectionEnum : TimepieceEnum.values()){
            if(connectionEnum.key.equals(key)){
                return connectionEnum;
            }
        }
        return TimepieceEnum.ZERO;
    }

}
