package com.cos.service.intf;

import com.cos.common.entity.BizResult;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.form.GoodsForm;
import com.cos.common.pojo.vo.GoodsVO;
import com.cos.pojo.request.GoodsRequest;

import java.util.List;

/**
 * @description: 商品service
 * @author: hzf
 * @date: 2018/2/4 下午5:44
 */
public interface GoodsService {

    /**
     * 插入单个商品记录
     * @param goodsForm
     * @throws CosmeticException
     */
    BizResult<String> insertGoods(GoodsForm goodsForm) throws CosmeticException;

    /**
     * 根据商品编号删除商品
     * @param goodsId
     * @throws CosmeticException
     */
    void deleteGoodsByGoodsID(String goodsId) throws CosmeticException;

    /**
     * 商品名模糊查询商品
     * @param name
     * @param pageNo
     * @param pageSize
     * @return
     * @throws CosmeticException
     */
    BizResult<List<GoodsVO>> selectGoodsByName(String name, Integer pageNo, Integer pageSize) throws CosmeticException;

    /**
     * 根据品牌名查询商品
     * @param name
     * @param pageNo
     * @param pageSize
     * @return
     * @throws CosmeticException
     */
    BizResult<List<GoodsVO>> selectGoodsByBrand(String name, Integer pageNo, Integer pageSize) throws CosmeticException;

    /**
     * 查询所有产品
     * @param pageNo
     * @param pageSize
     * @return
     * @throws CosmeticException
     */
    BizResult<List<GoodsVO>> selectAllGoods(Integer pageNo, Integer pageSize) throws CosmeticException;

    /**
     * 根据商品编号查询商品
     * @param goodsId
     * @return
     * @throws CosmeticException
     */
    GoodsVO selectGoodsByGoodsId(String goodsId) throws CosmeticException;

    /**
     * 根据查询条件组合查询商品
     * @param request
     * @return
     */
    BizResult<List<GoodsVO>> selectGoodsByConditions(GoodsRequest request) throws CosmeticException;

    /**
     * 根据商品编号更新商品
     * @param request
     * @throws CosmeticException
     */
    void updateGoodsByGoodsId(GoodsRequest request) throws CosmeticException;


}
