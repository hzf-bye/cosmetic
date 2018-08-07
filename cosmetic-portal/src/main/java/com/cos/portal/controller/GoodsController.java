package com.cos.portal.controller;

import com.cos.common.entity.BizResult;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.form.GoodsForm;
import com.cos.portal.service.intf.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.Size;

/**
 * @description: 商品信息controller
 * @author: hzf
 * @date: 2018/3/8 下午5:09
 */

@Slf4j
@Controller
@RequestMapping("portalGoods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @ResponseBody
    @RequestMapping("selectGoodsByConditions.json")
    public BizResult selectGoodsByConditions(@RequestBody GoodsForm goodsForm){

        try {
            return goodsService.selectGoodsByConditions(goodsForm);
        } catch (CosmeticException e) {
            log.error("根据品牌id查询所有商品列表信息失败", e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping("selectGoodsByGoodsId.json")
    public BizResult selectGoodsByGoodsId(@RequestBody GoodsForm goodsForm){

        try {
            return goodsService.selectGoodsByGoodsId(goodsForm);
        } catch (CosmeticException e) {
            log.error("根据商品id查询所有商品信息失败", e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }


}
