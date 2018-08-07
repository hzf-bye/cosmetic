package com.cos.rest.service.intf;

import com.cos.common.entity.BizResult;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.form.GoodsForm;
import com.cos.common.pojo.vo.GoodsVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 商品service
 * @author: hzf
 * @date: 2018/3/8 下午2:24
 */
public interface GoodsService {

    /**
     * 根据条件查询商品信息
     * @param goodsForm
     * @return
     * @throws CosmeticException
     */
    BizResult<List<GoodsVO>> selectGoodsByConditions(GoodsForm goodsForm) throws CosmeticException;

    /**
     * 根据商品编号查询商品
     * @param goodsForm
     * @return
     * @throws CosmeticException
     */
    GoodsVO selectGoodsByGoodsId(GoodsForm goodsForm) throws CosmeticException;

}
