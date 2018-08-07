package com.cos.dao.mapper.ext;

import com.cos.common.pojo.form.GoodsForm;
import com.cos.common.pojo.vo.GoodsVO;
import com.cos.dao.mapper.TbGoodsMapper;

import java.util.List;

/**
 * @description:
 * @author: hzf
 * @date: 2018/2/5 下午5:16
 */
public interface TbGoodsExtMapper extends TbGoodsMapper {

    /**
     * 通过品牌名查询商品信息
     * @param brandName
     * @return
     */
    List<GoodsVO> selectGoodsByBrandName(String brandName);

    /**
     * 商品名模糊查询商品
     * @param name
     * @return
     */
    List<GoodsVO> selectGoodsByName(String name);

    /**
     * 查询所有商品
     * @return
     */
    List<GoodsVO> selectAllGoods();

    /**
     * 根据查询条件组合查询商品
     * @param goodsForm
     * @return
     */
    List<GoodsVO> selectGoodsByConditions(GoodsForm goodsForm);
}
