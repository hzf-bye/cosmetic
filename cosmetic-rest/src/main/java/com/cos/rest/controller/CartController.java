package com.cos.rest.controller;

import com.alibaba.fastjson.JSON;
import com.cos.common.entity.BizResult;
import com.cos.common.enums.ErrorCodeEnum;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.form.CartForm;
import com.cos.common.pojo.vo.CartVO;
import com.cos.rest.service.intf.CartService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description: 服务层购物车controller
 * @author: hzf
 * @date: 2018/4/8 下午8:11
 */
@Slf4j
@RestController
@RequestMapping("restCart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 插入数据
     * @param cartForm
     * @return
     */
    @RequestMapping("insertCart")
    BizResult<List<CartVO>> insertCart(@RequestBody CartForm cartForm) {

        try {
            return cartService.insertCart(cartForm);
        } catch (CosmeticException e) {
            log.error("插入数据到购物车中失败 cartForm is {}", JSON.toJSONString(cartForm), e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }

    /**
     * 批量插入数据
     * @param cartForms
     * @return
     */
    @RequestMapping("insertCartList")
    BizResult<Boolean> insertCartList(@RequestBody List<CartForm> cartForms) {

        try {

            //插入数据前先清空购物车，因为购物车的数据已经全部展示给前台，所以可以得到所有购物车数据
            cartService.deleteCartByConditions(cartForms.get(0));
            cartForms.forEach(cartForm -> {
                cartService.insertCart(cartForm);
            });
            return BizResult.create(true);
        } catch (CosmeticException e) {
            log.error("插入数据到购物车中失败 cartForm is {}", JSON.toJSONString(cartForms), e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }

    /**
     * 通过条件查询数据
     * @param cartForm
     * @return
     */
    @RequestMapping("selectCartByConditions")
    BizResult<List<CartVO>> selectCartByConditions(@RequestBody CartForm cartForm) {

        //校验参数
        if (StringUtils.isBlank(cartForm.getUserId()) || cartForm.getMeetingPlaceId() == null) {
            return BizResult.create(ErrorCodeEnum.PARAM_ERROR.getErrCode(), "userId或meetingPlaceId为空");
        }

        try {
            return cartService.selectCartByConditions(cartForm);
        } catch (CosmeticException e) {
            log.error("查询购物车数据失败 cartForm is {}", JSON.toJSONString(cartForm), e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }

    /**
     * 通过id更新数据
     * @param cartForm
     * @return
     */
    @RequestMapping("updateCartById")
    BizResult<Boolean> updateCartById(@RequestBody CartForm cartForm) {

        try {
            return cartService.updateCartById(cartForm);
        } catch (CosmeticException e) {
            log.error("更新购物车数据失败 cartForm is {}", JSON.toJSONString(cartForm), e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }

    /**
     * 通过id删除数据
     * @param cartForm
     * @return
     */
    @RequestMapping("deleteCartById")
    BizResult<Boolean> deleteCartById(@RequestBody CartForm cartForm) {

        try {
            return cartService.deleteCartById(cartForm);
        } catch (CosmeticException e) {
            log.error("删除购物车数据失败 cartForm is {}", JSON.toJSONString(cartForm), e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }

    /**
     * 通过条件删除购物车数据
     * @param cartForm
     * @return
     */
    @RequestMapping("deleteCartByConditions")
    BizResult<Boolean> deleteCartByConditions(@RequestBody CartForm cartForm) {

        //校验参数
        if (StringUtils.isBlank(cartForm.getUserId()) || cartForm.getMeetingPlaceId() == null) {
            log.error("删除购物车数据失败 cartForm is {}", JSON.toJSONString(cartForm));
            return BizResult.create(ErrorCodeEnum.PARAM_ERROR);
        }

        try {
            return cartService.deleteCartByConditions(cartForm);
        } catch (CosmeticException e) {
            log.error("删除购物车数据失败 cartForm is {}", JSON.toJSONString(cartForm), e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }

}
