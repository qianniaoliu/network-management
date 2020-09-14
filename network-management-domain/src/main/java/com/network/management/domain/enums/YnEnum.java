package com.network.management.domain.enums;

/**
 * @author yusheng
 */
public enum YnEnum {

    /**
     * 有效
     */
    YES(1),

    /**
     * 无效
     */
    NO(0);

    private Integer code;

    YnEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
