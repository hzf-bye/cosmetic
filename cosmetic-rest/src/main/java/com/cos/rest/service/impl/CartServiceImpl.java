package com.cos.rest.service.impl;

import com.alibaba.fastjson.JSON;
import com.cos.common.entity.BizResult;
import com.cos.common.enums.ErrorCodeEnum;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.form.CartForm;
import com.cos.common.pojo.vo.CartVO;
import com.cos.common.utils.BeanUtil;
import com.cos.dao.mapper.TbCartMapper;
import com.cos.dao.mapper.TbGoodsColorMapper;
import com.cos.dao.mapper.ext.TbGoodsExtMapper;
import com.cos.dao.pojo.*;
import com.cos.rest.service.intf.CartService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 购物车service实现类
 * @author: hzf
 * @date: 2018/4/8 下午7:32
 */
@Slf4j
@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private TbCartMapper cartMapper;

    @Autowired
    private TbGoodsColorMapper colorMapper;

    @Autowired
    private TbGoodsExtMapper goodsExtMapper;

    /**
     * 插入购物车数据
     * @param cartForm
     * @return
     * @throws CosmeticException
     */
    @Override
    public BizResult<List<CartVO>>insertCart(CartForm cartForm) throws CosmeticException {

        BizResult<List<CartVO>> bizResult = new BizResult<>();
        try {
            TbCart tbCart = BeanUtil.createCloneBean(cartForm, TbCart.class);
            cartMapper.insertSelective(tbCart);
            //分页查询
            PageHelper.startPage(cartForm.getPageNo(), cartForm.getPageSize());
            TbCartExample example = new TbCartExample();
            example.createCriteria().andUserIdEqualTo(cartForm.getUserId());
            List<TbCart> tbCarts = cartMapper.selectByExample(example);
            PageInfo<TbCart> pageInfo = new PageInfo<>(tbCarts);
            long total = pageInfo.getTotal();
            List<CartVO> cartVOS = tbCarts.stream().map(tbCart1 -> {
                CartVO cartVO = BeanUtil.createCloneBean(tbCart1, CartVO.class);
                return cartVO;
            }).collect(Collectors.toList());

            bizResult.setData(cartVOS);
            bizResult.setPageNo(cartForm.getPageNo());
            bizResult.setPageSize(cartForm.getPageSize());
            bizResult.setTotalCount((int)total);
            bizResult.setSuccess(true);
            return bizResult;
        } catch (Exception e) {
            throw new CosmeticException(ErrorCodeEnum.INSERT_FAIL, e);
        }
    }

    /**
     * 通过条件查询购物车数据
     * @param cartForm
     * @return
     * @throws CosmeticException
     */
    @Override
    public BizResult<List<CartVO>> selectCartByConditions(CartForm cartForm) throws CosmeticException {

        try {
            BizResult<List<CartVO>> bizResult = new BizResult<>();
            TbCartExample example = this.buildCartCondition(cartForm);
            //分页查询
            PageHelper.startPage(cartForm.getPageNo(), cartForm.getPageSize());
            List<TbCart> tbCarts = cartMapper.selectByExample(example);
            if (CollectionUtils.isEmpty(tbCarts)) {
                return BizResult.create(Lists.newArrayList());
            }

            PageInfo<TbCart> pageInfo = new PageInfo<>(tbCarts);
            long total = pageInfo.getTotal();

            List<CartVO> cartVOS = tbCarts.stream().map(tbCart -> {
                CartVO cartVO = BeanUtil.createCloneBean(tbCart, CartVO.class);
                return cartVO;
            }).collect(Collectors.toList());

            //查询商品对应库存
            cartVOS.forEach(cartVO -> {

                if (StringUtils.isNotBlank(cartVO.getColor())) {
                    //不空，则该商品有颜色属性，去查询颜色表获取库存
                    //根据商品编号以及颜色查询库存查询对应库存
                    TbGoodsColorExample colorExample = new TbGoodsColorExample();
                    colorExample.createCriteria().andGoodsIdEqualTo(cartVO.getGoodsId());
                    List<TbGoodsColor> tbGoodsColors = colorMapper.selectByExample(colorExample);
                    cartVO.setInventory(tbGoodsColors.get(0).getInventory());
                } else {
                    //为空直接根据goodsId查询商品表
                    TbGoodsExample goodsExample = new TbGoodsExample();
                    goodsExample.createCriteria().andGoodsIdEqualTo(cartVO.getGoodsId());
                    List<TbGoods> tbGoodsList = goodsExtMapper.selectByExample(goodsExample);
                    cartVO.setInventory(tbGoodsList.get(0).getInventory());
                }
            });

            bizResult.setData(cartVOS);
            bizResult.setPageNo(cartForm.getPageNo());
            bizResult.setPageSize(cartForm.getPageSize());
            bizResult.setTotalCount((int)total);
            bizResult.setSuccess(true);
            return bizResult;
        } catch (Exception e) {
            log.error("查询失败。 cartForm is {}", JSON.toJSONString(cartForm));
            throw new CosmeticException(ErrorCodeEnum.QUERY_FAIL, e);
        }
    }

    /**
     * 通过id更新购物车数据
     * @param cartForm
     * @return
     * @throws CosmeticException
     */
    @Override
    public BizResult<Boolean> updateCartById(CartForm cartForm) throws CosmeticException {

        try {
            TbCart tbCart = BeanUtil.createCloneBean(cartForm, TbCart.class);
            int count = cartMapper.updateByPrimaryKeySelective(tbCart);
            if (count != 1) {
                log.error("更新失败。 cartForm is {}", JSON.toJSONString(cartForm));
                throw new CosmeticException(ErrorCodeEnum.PARAM_ERROR);
            }
            return BizResult.create(Boolean.TRUE);

        } catch (CosmeticException e) {
            throw e;
        } catch (Exception e) {
            log.error("更新失败。 cartForm is {}", JSON.toJSONString(cartForm));
            throw new CosmeticException(ErrorCodeEnum.UPLOAD_ERROR, e);
        }
    }

    /**
     * 通过id删除购物车数据
     * @param cartForm
     * @return
     * @throws CosmeticException
     */
    @Override
    public BizResult<Boolean> deleteCartById(CartForm cartForm) throws CosmeticException {

        try {

            int count = cartMapper.deleteByPrimaryKey(cartForm.getId());
            if (count != 1) {
                log.error("删除失败。 cartForm is {}", JSON.toJSONString(cartForm));
                throw new CosmeticException(ErrorCodeEnum.PARAM_ERROR);
            }
            return BizResult.create(Boolean.TRUE);

        } catch (CosmeticException e) {
            throw e;
        } catch (Exception e) {
            log.error("更新失败。 cartForm is {}", JSON.toJSONString(cartForm));
            throw new CosmeticException(ErrorCodeEnum.EXCEPTION, e);
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
            cartMapper.deleteByExample(this.buildCartCondition(cartForm));
            return BizResult.create(true);
        } catch (Exception e) {
            log.error("删除失败. cartForm is {}", JSON.toJSONString(cartForm));
            throw new CosmeticException(ErrorCodeEnum.UPDATE_FAIL, e);
        }
    }

    /**
     * 构造查询购物车的查询条件
     *
     * @param form
     * @return
     */
    private TbCartExample buildCartCondition(CartForm form){

        TbCartExample example = new TbCartExample();
        example.createCriteria()
                .andMeetingPlaceIdEqualTo(form.getMeetingPlaceId())
                .andUserIdEqualTo(form.getUserId());
        return example;
    }
}
