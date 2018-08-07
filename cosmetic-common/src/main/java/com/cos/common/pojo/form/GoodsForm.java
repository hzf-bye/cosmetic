package com.cos.common.pojo.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description: 商品form
 * @author: hzf
 * @date: 2018/2/4 下午5:24
 */
@Data
public class GoodsForm extends BaseForm{

    /**
     * 商品编号
     */
    private String goodsId;

    /**
     * 商品短标题
     */
    @NotBlank( message = "商品短标题不能为空")
    private String shortName;

    /**
     * 商品标题
     */
    @NotBlank( message = "商品标题不能为空")
    private String shopName;

    /**
     * 品牌id
     */
    @NotNull( message = "品牌id不能为空")
    private Long brandId;

    /**
     * 品牌名
     */
    private String brandName;

    /**
     * 商品价格
     */
    @NotNull( message = "价格不能为空")
    private Long price;

    /**
     * 商品库存
     */
    @NotNull( message = "库存不能为空")
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
    @NotBlank( message = "主页图片路径不能为空")
    private String mainPic;

    /**
     * 商品详情页头部图片
     */
    @NotBlank( message = "商品详情页头部图片不能为空")
    private String headPic;

    /**
     * 商品详情页详情图
     */
    @NotEmpty( message = "商品详情页详情图不能为空")
    private List<String>  detailPic;

    /**
     * 商品颜色相关数据
     */
    @Valid
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
