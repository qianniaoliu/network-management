package com.network.management.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yusheng
 */
@Getter
@AllArgsConstructor
public enum ConnectionEnum {

    /**
     * 未连接
     */
    NO(0, "未连接"),

    /**
     * 已连接
     */
    YES(1, "已连接");

    private Integer key;
    private String desc;

    public static ConnectionEnum getByKey(Integer key){
        for(ConnectionEnum connectionEnum : ConnectionEnum.values()){
            if(connectionEnum.key.equals(key)){
                return connectionEnum;
            }
        }
        return ConnectionEnum.NO;
    }

}
