package com.cos.common.pojo.form;


import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @description: 购物车form
 * @author: hzf
 * @date: 2018/4/8 下午7:35
 */
@Data
public class CartForm extends BaseForm{

    /**
     * 购物车记录主键
     */
    private Long id;

    /**
     * 用户id
     */
    @NotBlank(message = "用户id不能为空")
    private String userId;

    /**
     * 品牌id
     */
    @NotNull(message = "品牌id不能为空")
    private Long brandId;

    /**
     * 商品id
     */
    @NotBlank(message = "商品id不能为空")
    private String goodsId;

    /**
     * 商品数量
     */
    @NotNull(message = "商品数量不能为空")
    private Integer num;

    /**
     * 商品颜色
     */
    private String color;

    /**
     * 商品名
     */
    @NotBlank(message = "商品名不能为空")
    private String goodsName;

    /**
     * 商品单价
     */
    @NotNull(message = "商品单价不能为空")
    private Integer price;

    /**
     * 商品品牌名
     */
    @NotBlank(message = "商品品牌名不能为空")
    private String brandName;

    /**
     * 会场id
     */
    @NotNull(message = "会场id不能为空")
    private Long meetingPlaceId;

}
