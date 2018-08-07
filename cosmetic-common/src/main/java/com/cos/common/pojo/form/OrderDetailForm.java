package com.cos.common.pojo.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @description: 订单明细form
 * @author: hzf
 * @date: 2018/2/7 下午3:55
 */
@Data
public class OrderDetailForm {

    /**
     * 商品id
     */
    @NotBlank(message = "商品id不能为空")
    private String goodsId;

    /**
     * 商品名称
     */
    private String GoodsName;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 商品数量
     */
    @NotNull(message = "商品id不能为空")
    private Long num;

    /**
     * 商品总金额
     */
    private Long totalFee;

    /**
     * 商品单价
     */
    @NotNull(message = "商品单价不能为空")
    private Long price;

    /**
     * 商品颜色
     */
    private String color;

}
