package com.cos.common.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * @description: 前台页面会场商品列表VO
 * @author: hzf
 * @date: 2018/3/8 上午10:37
 */
@Data
public class GoodsListVO {

    /**
     * 会场id
     */
    private Long id;

    /**
     * 品牌id
     */
    private String brandId;

    /**
     * 会场名
     */
    private String name;

    /**
     * 会差开始时间，时间的毫秒数
     */
    private Long startTime;

    /**
     * 会差结束时间，时间的毫秒数
     */
    private Long endTime;

    /**
     * 商品编号
     */
    private String goodsId;

    /**
     * 商品价格
     */
    private Long price;

    /**
     * 商品短标题
     */
    private String shortName;

    /**
     * 主页图片路径
     */
    private String mainPic;

    /**
     * 会场里的商品信息
     */
    List<GoodsVO> goodsVOList;

}
