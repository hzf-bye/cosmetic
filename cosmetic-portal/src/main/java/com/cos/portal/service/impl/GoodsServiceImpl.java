package com.cos.portal.service.impl;

import com.alibaba.fastjson.JSON;
import com.cos.common.entity.BizResult;import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.form.GoodsForm;
import com.cos.common.pojo.vo.GoodsVO;
import com.cos.common.utils.HttpClientUtil;
import com.cos.common.utils.HttpClientUtils;
import com.cos.dao.mapper.TbGoodsMapper;
import com.cos.dao.mapper.ext.TbGoodsExtMapper;
import com.cos.portal.service.intf.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 商品service实现类
 * @author: hzf
 * @date: 2018/3/8 下午3:03
 */
@Service
@Slf4j
public class GoodsServiceImpl implements GoodsService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Value("${SELECT_GOODS_LIST}")
    private String SELECT_GOODS_LIST;

    @Value("${SELECT_GOODS_BY_ID}")
    private String SELECT_GOODS_BY_ID;

    /**
     * 根据条件查询商品信息
     * @param goodsForm
     * @return
     * @throws CosmeticException
     */
    @Override
    public BizResult<List<GoodsVO>> selectGoodsByConditions(GoodsForm goodsForm) throws CosmeticException {

        try {
            //调用服务
            String json = HttpClientUtils.doPostJson(REST_BASE_URL + SELECT_GOODS_LIST, JSON.toJSONString(goodsForm));
            BizResult bizResult = JSON.parseObject(json, BizResult.class);
            return bizResult;
        } catch (Exception e) {
            log.error("调用服务层通过品牌id查询商品接口失败", e);
            return BizResult.create("9999", e.getMessage());
        }

    }

    /**
     * 根据商品编号查询商品
     *
     * @param goodsForm
     * @return
     * @throws CosmeticException
     */
    @Override
    public BizResult selectGoodsByGoodsId(GoodsForm goodsForm) throws CosmeticException {

        try {
            //调用服务
            String json = HttpClientUtils.doPostJson(REST_BASE_URL + SELECT_GOODS_BY_ID, JSON.toJSONString(goodsForm));
            BizResult bizResult = JSON.parseObject(json, BizResult.class);
            return bizResult;
        } catch (Exception e) {
            log.error("调用服务层通过商品id查询商品接口失败", e);
            return BizResult.create("9999", e.getMessage());
        }
    }
}
