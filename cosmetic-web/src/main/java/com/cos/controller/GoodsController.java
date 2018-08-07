package com.cos.controller;

import com.alibaba.fastjson.JSON;
import com.cos.common.entity.BizResult;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.form.GoodsForm;
import com.cos.common.pojo.vo.GoodsVO;
import com.cos.common.utils.JsonFormValidatorUtil;
import com.cos.pojo.request.BaseRequest;
import com.cos.pojo.request.GoodsRequest;
import com.cos.service.intf.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;


/**
 * @description: 商品controller
 * @author: hzf
 * @date: 2018/2/5 下午4:01
 */
@RequestMapping("/goods")
@Controller
@Slf4j
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 插入单个商品记录
     * @param goodsForm
     * @return
     */
    @RequestMapping("/insert")
    @ResponseBody
    public BizResult insertGoods(@RequestBody @Valid GoodsForm goodsForm, Errors errors){
        try {
            JsonFormValidatorUtil.validate(errors);
            BizResult bizResult = goodsService.insertGoods(goodsForm);
            return bizResult;
        } catch (CosmeticException e) {
            log.error("插入商品错误,goodsForm is {}", JSON.toJSONString(goodsForm), e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }

    /**
     * 根据商品id删除商品记录
     * @param request
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public BizResult deleteGoodsByGoodsId(@RequestBody GoodsRequest request){
        try {

            if (StringUtils.isBlank(request.getGoodsId())) {
                log.error("商品编号为空");
                return BizResult.create("9999", "商品编号为空");
            }
            goodsService.deleteGoodsByGoodsID(request.getGoodsId());
            BizResult bizResult = new BizResult();
            bizResult.setSuccess(true);
            return bizResult;
        } catch (CosmeticException e) {
            log.error("删除商品错误,goodsId is {}",request.getGoodsId(), e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }

    /**
     * 根据名称模糊查询商品信息
     * @param request
     * @return
     */
    @RequestMapping("/selectGoodsByName")
    @ResponseBody
    public BizResult selectGoodsByName(@RequestBody GoodsRequest request){
        try {
            BizResult bizResult = goodsService.selectGoodsByName(request.getShopName(),
                    request.getPageNo(), request.getPageSize());
            return bizResult;
        } catch (CosmeticException e) {
            log.error("根据名称模糊查询商品信息错误,name is {}",request.getShopName(), e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }

    /**
     *  根据品牌名查询商品信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/selectGoodsByBrand")
    @ResponseBody
    public BizResult selectGoodsByBrand(@RequestBody GoodsRequest request){
        try {
            BizResult bizResult = goodsService.selectGoodsByBrand(request.getBrandName(),
                    request.getPageNo(), request.getPageSize());
            return bizResult;
        } catch (CosmeticException e) {
            log.error("根据品牌名查询商品信息错误,brandName is {}",request.getBrandName(), e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }

    /**
     * 查询所有商品
     *
     * @param request
     * @return
     */
    @RequestMapping("/selectAllGoods")
    @ResponseBody
    public BizResult selectAllGoods(@RequestBody BaseRequest request){
        try {
            BizResult bizResult = goodsService.selectAllGoods(request.getPageNo(), request.getPageSize());
            return bizResult;
        } catch (CosmeticException e) {
            log.error("查询所有商品错误", e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }
    /**
     * 根据商品编号查询商品
     * @param request
     * @return
     */
    @RequestMapping("/selectGoodsByGoodsId")
    @ResponseBody
    public BizResult selectGoodsByGoodsId(@RequestBody GoodsRequest request){
        try {
            GoodsVO goodsVO = goodsService.selectGoodsByGoodsId(request.getGoodsId());
            return BizResult.create(goodsVO);
        } catch (CosmeticException e) {
            log.error("根据商品编号查询商品,goodsId is {}",request.getGoodsId(), e);
            return BizResult.create(e.getCode(), e.getMessage());
        }

    }

    /**
     * 根据查询条件组合查询商品
     * @param request
     * @return
     */
    @RequestMapping("selectGoodsByConditions")
    @ResponseBody
    public BizResult selectGoodsByConditions(@RequestBody GoodsRequest request){
        try {
            BizResult bizResult = goodsService.selectGoodsByConditions(request);
            return bizResult;
        } catch (CosmeticException e) {
            log.error("根据查询条件组合查询商品,goodsForm is {}", JSON.toJSONString(request), e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }

    /**
     * 根据商品编号更新商品信息
     * @param request
     * @return
     */
    @RequestMapping("updateGoodsByGoodsId")
    @ResponseBody
    public BizResult updateGoodsByGoodsId(@RequestBody GoodsRequest request){
        try {
            goodsService.updateGoodsByGoodsId(request);
            BizResult bizResult = new BizResult();
            bizResult.setSuccess(true);
            return bizResult;
        } catch (CosmeticException e) {
            log.error("根据商品编号更新商品信息,goodsForm is {}",request, e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }

}
