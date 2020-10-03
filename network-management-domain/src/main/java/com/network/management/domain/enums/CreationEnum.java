package com.network.management.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yusheng
 */
@Getter
@AllArgsConstructor
public enum CreationEnum {

    /**
     * 未建立
     */
    NO(0, "未建立"),

    /**
     * 已建立
     */
    YES(1, "已建立");

    private Integer key;
    private String desc;

    public static CreationEnum getByKey(Integer key){
        for(CreationEnum connectionEnum : CreationEnum.values()){
            if(connectionEnum.key.equals(key)){
                return connectionEnum;
            }
        }
        return CreationEnum.NO;
    }

}
