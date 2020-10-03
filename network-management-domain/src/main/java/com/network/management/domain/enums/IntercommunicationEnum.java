package com.network.management.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yusheng
 */
@Getter
@AllArgsConstructor
public enum IntercommunicationEnum {

    /**
     * 未连通
     */
    NO(0, "未连通"),

    /**
     * 已连通
     */
    YES(1, "已连通");

    private Integer key;
    private String desc;

    public static IntercommunicationEnum getByKey(Integer key){
        for(IntercommunicationEnum connectionEnum : IntercommunicationEnum.values()){
            if(connectionEnum.key.equals(key)){
                return connectionEnum;
            }
        }
        return IntercommunicationEnum.NO;
    }

}
