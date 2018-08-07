package com.cos.common.pojo.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;


/**
 * @description: 商品颜色form
 * @author: hzf
 * @date: 2018/2/6 上午10:13
 */
@Data
public class GoodsColorForm {

    /**
     * 商品颜色id
     */

    private Long goodsColorId;
    /**
     * 商品库存
     */
    @NotNull( message = "商品库存不能为空")
    private Long inventory;

    /**
     * 商品颜色
     */
    @NotBlank( message = "商品颜色不能为空")
    private String colorName;

}
