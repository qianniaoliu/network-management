package com.network.management.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 电源控制命令
 * @author yyc
 * @date 2021/12/19 18:31
 */
@AllArgsConstructor
@Getter
public enum PowerCmdEnum {
    CMD_QUERY(1,"5A 5A 7E E7 FB 46 06 01 94 D8", "查询命令"),
    CMD_DC(2, "5A A5 7E E7 FB 43 08 01 00 03 2F 34", "切换直流命令"),
    CMD_AC(3, "5A A5 7E E7 FB 43 08 01 00 00 1F 57", "切换交流命令");

    private Integer type;
    /**
     * 电源命令
     */
    private String cmd;
    private String desc;

    /**
     * 根据操作类型获取操作命令
     * @param type 操作类型
     * @return
     */
    public static String getCmd(Integer type){
        if(Objects.nonNull(type)){
            for(PowerCmdEnum cmdEnum : values()){
                if(cmdEnum.getType().equals(type)){
                    return cmdEnum.getCmd();
                }
            }
        }
        return null;
    }

    /**
     * 判断是否控制命令
     * @param type 操作类型
     * @return
     */
    public static Boolean isControlCmd(Integer type){
        return PowerCmdEnum.CMD_DC.getType().equals(type) || PowerCmdEnum.CMD_AC.getType().equals(type);
    }
}
