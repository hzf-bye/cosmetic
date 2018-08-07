package com.cos.common.pojo.vo;

import lombok.Data;

/**
 * @description: 购物车VO
 * @author: hzf
 * @date: 2018/4/8 下午7:35
 */
@Data
public class CartVO {

    /**
     * 购物车记录主键
     */
    private Long id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 品牌id
     */
    private Long brandId;

    /**
     * 商品id
     */
    private String goodsId;

    /**
     * 商品数量
     */
    private Integer num;

    /**
     * 商品颜色
     */
    private String color;

    /**
     * 商品名
     */
    private String goodsName;

    /**
     * 商品单价
     */
    private Integer price;

    /**
     * 商品品牌名
     */
    private String brandName;

    /**
     * 商品对应的库存
     */
    private Long inventory;

}
