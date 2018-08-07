package com.cos.common.pojo.vo;

import lombok.Data;


/**
 * @description: 订单明细vo
 * @author: hzf
 * @date: 2018/2/7 下午3:15
 */
@Data
public class OrderDetailVO {

    /**
     * 商品id
     */
    private Long goodsId;

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
    private Long num;

    /**
     * 商品总金额,单位分
     */
    private Long totalFee;

    /**
     * 商品总金额,单位元
     */
    private String totalFeeYuan;

    /**
     * 商品单价，单位分
     */
    private Long price;

    /**
     * 商品单价，单位元
     */
    private String priceYuan;

    /**
     * 商品主图
     */
    private String mainPic;

    /**
     * 商品重量
     */
    private Integer weight;

    /**
     * 商品颜色
     */
    private String color;
}
