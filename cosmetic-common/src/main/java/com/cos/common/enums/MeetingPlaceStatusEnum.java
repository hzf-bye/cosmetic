package com.cos.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 会场状态枚举类
 * @author: hzf
 * @date: 2018/2/27 下午9:42
 */
@AllArgsConstructor
public enum MeetingPlaceStatusEnum {

    UNPUBLISHED(1, "未发布"),

    NO_START(2, "未开始"),

    PROCESSING(3, "进行中"),

    OVER(4, "已结束"),;

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
