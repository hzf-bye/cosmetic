package com.cos.pojo.request;

import com.cos.common.pojo.form.GoodsColorForm;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description:
 * @author: hzf
 * @date: 2018/4/1 下午8:18
 */
@Data
public class GoodsRequest extends BaseRequest{

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
     * 品牌id
     */
    private Long brandId;

    /**
     * 品牌名
     */
    private String brandName;

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
     * 商品详情页头部图片
     */
    private String headPic;

    /**
     * 商品详情页详情图
     */
    private List<String> detailPic;

    /**
     * 商品颜色相关数据
     */
    private List<GoodsColorForm>  goodsColor;

    /**
     * 标识商品是否有颜色属性 1:有 0:无
     */
    private Integer color;

    /**
     * 是否下架 0:否 1:是
     */
    private Integer isDeleted;
}
