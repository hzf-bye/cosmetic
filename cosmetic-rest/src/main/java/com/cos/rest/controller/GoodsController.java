package com.cos.rest.controller;

import com.alibaba.fastjson.JSON;
import com.cos.common.entity.BizResult;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.form.GoodsForm;
import com.cos.common.pojo.vo.GoodsVO;
import com.cos.rest.service.intf.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 商品controller
 * @author: hzf
 * @date: 2018/3/8 下午3:37
 */
@Slf4j
@Controller
@RequestMapping("restGoods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @ResponseBody
    @RequestMapping("selectGoodsByConditions")
    public BizResult selectGoodsByConditions(@RequestBody GoodsForm goodsForm){

        try {
            return goodsService.selectGoodsByConditions(goodsForm);
        } catch (CosmeticException e) {
            log.error("根据brangId查询商品列表信息失败 goodsForm is {}", JSON.toJSONString(goodsForm), e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping("selectGoodsByGoodsId")
    public BizResult selectGoodsByGoodsId(@RequestBody GoodsForm goodsForm){

        try {
           GoodsVO goodsVO = goodsService.selectGoodsByGoodsId(goodsForm);
            return BizResult.create(goodsVO);
        } catch (CosmeticException e) {
            log.error("根据id查询商品信息失败 goodsForm is {}", JSON.toJSONString(goodsForm), e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }
}
