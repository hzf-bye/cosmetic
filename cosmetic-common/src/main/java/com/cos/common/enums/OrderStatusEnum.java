package com.cos.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 订单状态枚举类
 * @author: hzf
 * @date: 2018/2/7 下午3:58
 */
@AllArgsConstructor
public enum OrderStatusEnum {

    UNPAID(1, "未付款"),

    UN_SHIPPED(2, "未发货"),

    SHIPPED(3, "已发货"),

    TRADE_SUCCESS(4, "交易成功"),

    TRADE_CLOSE(5, "交易关闭"),;

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
