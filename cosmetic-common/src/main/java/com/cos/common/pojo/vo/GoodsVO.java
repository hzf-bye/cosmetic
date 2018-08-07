package com.cos.common.pojo.vo;

import com.cos.common.query.BaseQuery;
import lombok.Data;

import java.util.List;

/**
 * @description: 商品vo
 * @author: hzf
 * @date: 2018/2/4 下午5:31
 */
@Data
public class GoodsVO extends BaseQuery {

    /**
     * 商品编号
     */
    private String goodsId;
    /**
     * 商品短标题
     */
    private String shortName;

    /**
     * 商品标题
     */
    private String shopName;

    /**
     * 品牌名
     */
    private String brandName;

    /**
     * 品牌id
     */
    private Long brandId;

    /**
     * 商品价格
     */
    private Long price;

    /**
     * 商品库存
     */
    private Long inventory;

    /**
     * 商品卖点
     */
    private String sellPoint;

    /**
     * 商品重量
     */
    private Integer weight;

    /**
     * 商品供应价
     */
    private Long supplyPrice;

    /**
     * 主页图片路径
     */
    private String mainPic;

    /**
     * 商品详情头部图片路径
     */
    private String headPic;

    /**
     * 商品描述图片路径
     */
    private List<String> detailPic;

    /**
     * 商品描述图片路径,json格式
     */
    private String detailPicJson;
    /**
     * 商品颜色相关数据
     */
    private List<GoodsColorVO> goodsColor;

    /**
     * 标识商品是否有颜色属性 1:有 0:无
     */
    private Integer color;

    /**
     * 是否下架 0:否 1:是
     */
    private Integer isDeleted;


}
