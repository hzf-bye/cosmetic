package com.cos.common.pojo.vo;

import lombok.Data;

/**
 * @description: 商品颜色vo
 * @author: hzf
 * @date: 2018/2/6 上午10:51
 */
@Data
public class GoodsColorVO {

    /**
     * 商品库存
     */
    private Long inventory;

    /**
     * 商品颜色
     */
    private String colorName;
}
