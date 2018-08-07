package com.cos.common.pojo.vo;

import lombok.Data;

/**
 * @description: 品牌vo
 * @author: hzf
 * @date: 2018/2/7 上午9:38
 */
@Data
public class BrandVO {

    /**
     * 品牌id
     */
    private Long id;

    /**
     * 品牌名
     */
    private String brandName;

    /**
     * 品牌销量
     */
    private Integer salesVolume;

    /**
     * 是否下架 1：是， 0：否
     */
    private Integer isDeleted;
}
