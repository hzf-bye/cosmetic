package com.cos.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 状态枚举类
 * @author: hzf
 * @date: 2018/1/20 下午4:46
 */
@AllArgsConstructor
public enum CommonStatusEnum {

    NO(0, "否"),

    YES(1, "是"),;

    @Getter
    private Integer code;

    @Getter
    private String name;

    public static CommonStatusEnum get(int code) {
        for (CommonStatusEnum attr : CommonStatusEnum.values()) {
            if (attr.getCode() == code) {
                return attr;
            }
        }
        throw new IllegalArgumentException("No such status.");
    }

    public static CommonStatusEnum get(String name) {
        if (null == name || ("").equals(name.trim())) {
            return null;
        }
        for (CommonStatusEnum attr : CommonStatusEnum.values()) {
            if (name.equals(attr.getName())) {
                return attr;
            }
        }
        throw new IllegalArgumentException("No such status.");
    }
}

