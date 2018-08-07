package com.cos.portal.service.impl;

import com.alibaba.fastjson.JSON;
import com.cos.common.entity.BizResult;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.form.CartForm;
import com.cos.common.pojo.vo.CartVO;
import com.cos.common.utils.HttpClientUtils;
import com.cos.portal.service.intf.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 服务层购物车service实现类
 * @author: hzf
 * @date: 2018/4/8 下午7:32
 */
@Slf4j
@Service
public class CartServiceImpl implements CartService{

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Value("${INSERT_CART}")
    private String INSERT_CART;

    @Value("${INSERT_CART_LIST}")
    private String INSERT_CART_LIST;

    @Value("${SELECT_CART_BY_CDT}")
    private String SELECT_CART_BY_CDT;

    @Value("${UPDATE_CART_BY_ID}")
    private String UPDATE_CART_BY_ID;

    @Value("${DELETE_CART_BY_ID}")
    private String DELETE_CART_BY_ID;

    @Value("${DELETE_CART_BY_CDT}")
    private String DELETE_CART_BY_CDT;

    /**
     * 插入购物车数据
     *
     * @param cartForm
     * @return
     * @throws CosmeticException
     */
    @Override
    public BizResult<List<CartVO>> insertCart(CartForm cartForm) throws CosmeticException {

        try {
            //调用服务
            String json = HttpClientUtils.doPostJson(REST_BASE_URL + INSERT_CART, JSON.toJSONString(cartForm));
            BizResult bizResult = JSON.parseObject(json, BizResult.class);
            return bizResult;
        } catch (Exception e) {
            log.error("调用服务层插入数据至购物车的接口失败", e);
            return BizResult.create("9999", e.getMessage());
        }
    }

    /**
     * 批量插入购物车数据
     *
     * @param cartForms
     * @return
     * @throws CosmeticException
     */
    @Override
    public BizResult insertCartList(List<CartForm> cartForms) throws CosmeticException {

        try {
            //调用服务
            String json = HttpClientUtils.doPostJson(REST_BASE_URL + INSERT_CART_LIST, JSON.toJSONString(cartForms));
            return JSON.parseObject(json, BizResult.class);
        } catch (Exception e) {
            log.error("调用服务层插入数据至购物车的接口失败", e);
            return BizResult.create("9999", e.getMessage());
        }
    }

    /**
     * 通过id查询购物车数据
     *
     * @param cartForm
     * @return
     * @throws CosmeticException
     */
    @Override
    public BizResult<List<CartVO>> selectCartByConditions(CartForm cartForm) throws CosmeticException {

        try {
            //调用服务
            String json = HttpClientUtils.doPostJson(REST_BASE_URL + SELECT_CART_BY_CDT, JSON.toJSONString(cartForm));
            BizResult bizResult = JSON.parseObject(json, BizResult.class);
            return bizResult;
        } catch (Exception e) {
            log.error("调用服务层通过条件查询购物车的接口失败", e);
            return BizResult.create("9999", e.getMessage());
        }
    }

    /**
     * 通过id更新购物车数据
     *
     * @param cartForm
     * @return
     * @throws CosmeticException
     */
    @Override
    public BizResult<Boolean> updateCartById(CartForm cartForm) throws CosmeticException {

        try {
            //调用服务
            String json = HttpClientUtils.doPostJson(REST_BASE_URL + UPDATE_CART_BY_ID, JSON.toJSONString(cartForm));
            BizResult bizResult = JSON.parseObject(json, BizResult.class);
            return bizResult;
        } catch (Exception e) {
            log.error("调用服务层通过id更新购物车数据的接口失败", e);
            return BizResult.create("9999", e.getMessage());
        }
    }

    /**
     * 通过id删除购物车数据
     *
     * @param cartForm
     * @return
     * @throws CosmeticException
     */
    @Override
    public BizResult<Boolean> deleteCartById(CartForm cartForm) throws CosmeticException {

        try {
            //调用服务
            String json = HttpClientUtils.doPostJson(REST_BASE_URL + DELETE_CART_BY_ID, JSON.toJSONString(cartForm));
            BizResult bizResult = JSON.parseObject(json, BizResult.class);
            return bizResult;
        } catch (Exception e) {
            log.error("调用服务层通过id删除购物车数据的接口失败", e);
            return BizResult.create("9999", e.getMessage());
        }
    }

    /**
     * 通过条件删除购物车数据
     *
     * @param cartForm
     * @return
     * @throws CosmeticException
     */
    @Override
    public BizResult<Boolean> deleteCartByConditions(CartForm cartForm) throws CosmeticException {

        try {
            //调用服务
            String json = HttpClientUtils.doPostJson(REST_BASE_URL + DELETE_CART_BY_CDT, JSON.toJSONString(cartForm));
            BizResult bizResult = JSON.parseObject(json, BizResult.class);
            return bizResult;
        } catch (Exception e) {
            log.error("调用服务层通过条件删除购物车数据的接口失败", e);
            return BizResult.create("9999", e.getMessage());
        }
    }
}
