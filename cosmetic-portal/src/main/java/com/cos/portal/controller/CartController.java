package com.cos.portal.controller;

import com.alibaba.fastjson.JSON;
import com.cos.common.entity.BizResult;
import com.cos.common.enums.ErrorCodeEnum;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.form.CartForm;
import com.cos.common.pojo.vo.CartVO;
import com.cos.common.utils.JsonFormValidatorUtil;
import com.cos.common.utils.ValidationResult;
import com.cos.common.utils.ValidationUtils;
import com.cos.portal.service.intf.CartService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description: 服务层购物车controller
 * @author: hzf
 * @date: 2018/4/8 下午7:30
 */
@Slf4j
@RestController
@RequestMapping("portalCart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 插入数据
     * @param cartForm
     * @return
     */
    @RequestMapping("insertCart.json")
    BizResult<List<CartVO>> insertCart(@RequestBody @Valid CartForm cartForm, Errors errors) {

        try {
            JsonFormValidatorUtil.validate(errors);
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
    @RequestMapping("insertCartList.json")
    BizResult insertCartList(@RequestBody CartForm[] cartForms) {

        // 遍历校验请求参数
        for (CartForm cartForm : cartForms) {
            ValidationResult validationResult = ValidationUtils.validateEntity(cartForm);
            if (validationResult.isHasErrors()) {
                return BizResult.create(ErrorCodeEnum.PARAM_ERROR.getErrCode(),
                        JSON.toJSONString(validationResult.getErrorMsg()));
            }
        }

        try {
            return cartService.insertCartList(Arrays.asList(cartForms));
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
    @RequestMapping("selectCartByConditions.json")
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
    @RequestMapping("updateCartById.json")
    BizResult<Boolean> updateCartById(@RequestBody CartForm cartForm) {

        try {
            if (cartForm.getId() == null) {
                return BizResult.create("9999", "购物车id不能为空");
            }
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
    @RequestMapping("deleteCartById.json")
    BizResult<Boolean> deleteCartById(@RequestBody CartForm cartForm) {

        try {
            if (cartForm.getId() == null) {
                return BizResult.create("9999", "购物车id不能为空");
            }
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
    @RequestMapping("deleteCartByConditions.json")
    BizResult<Boolean> deleteCartByConditions(@RequestBody CartForm cartForm) {

        //校验参数
        if (StringUtils.isBlank(cartForm.getUserId()) || cartForm.getMeetingPlaceId() == null) {
            log.error("删除购物车数据失败 cartForm is {}", JSON.toJSONString(cartForm));
            return BizResult.create(ErrorCodeEnum.PARAM_ERROR);
        }

        try {
            return cartService.deleteCartByConditions(cartForm);
        } catch (CosmeticException e) {
            log.error("删除购物车数据失败 cartForm is {}", JSON.toJSONString(cartForm));
            return BizResult.create(ErrorCodeEnum.PARAM_ERROR);
        }

    }

}
