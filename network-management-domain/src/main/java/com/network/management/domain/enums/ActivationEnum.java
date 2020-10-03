package com.network.management.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yusheng
 */
@Getter
@AllArgsConstructor
public enum ActivationEnum {

    /**
     * 未激活
     */
    NO(0, "未激活"),

    /**
     * 已激活
     */
    YES(1, "已激活");

    private Integer key;
    private String desc;

    public static ActivationEnum getByKey(Integer key){
        for(ActivationEnum connectionEnum : ActivationEnum.values()){
            if(connectionEnum.key.equals(key)){
                return connectionEnum;
            }
        }
        return ActivationEnum.NO;
    }

}
