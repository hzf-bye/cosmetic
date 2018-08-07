package com.cos.service;

import com.cos.base.BaseTest;
import com.cos.common.entity.BizResult;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.form.GoodsColorForm;
import com.cos.common.pojo.form.GoodsForm;
import com.cos.common.pojo.vo.GoodsVO;
import com.cos.pojo.request.GoodsRequest;
import com.cos.service.intf.GoodsService;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.List;


/**
 * @description: 商品service测试类
 * @author: hzf
 * @date: 2018/2/5 下午3:25
 */

public class GoodsServiceTest extends BaseTest {

    @Autowired
    private GoodsService goodsService;

    @Test
    public void insertGoodsTest(){

        try {
            GoodsForm goodsForm = new GoodsForm();
            goodsForm.setBrandId(6L);
            goodsForm.setInventory(100l);
            goodsForm.setPrice(100l);
            goodsForm.setSellPoint("乳液面霜 补水保湿 活力抗倦 男士化妆品");
            goodsForm.setWeight(50);
            goodsForm.setSupplyPrice(80l);
            goodsForm.setShopName("妮维雅(NIVEA)男士Q10活力劲肤露50g（乳液面霜 补水保湿 活力抗倦 男士化妆品）");
            goodsForm.setShortName("妮维雅(NIVEA)男士Q10活力劲肤露50g");
            goodsForm.setMainPic("890");
            goodsForm.setHeadPic("789");
            List<String> detailPics = Lists.newArrayList();
            detailPics.add("tre");
            detailPics.add("890");
            //goodsForm.setDetailPic(detailPics);
            List<GoodsColorForm> goodsColorForms = Lists.newArrayList();
            GoodsColorForm goodsColorForm1 = new GoodsColorForm();
            GoodsColorForm goodsColorForm2 = new GoodsColorForm();
            goodsColorForm1.setColorName("白色");
            goodsColorForm1.setInventory(89L);
            goodsColorForm2.setColorName("红色");
            goodsColorForm2.setInventory(89L);
            goodsColorForms.add(goodsColorForm1);
            goodsColorForms.add(goodsColorForm2);
            //goodsForm.setGoodsColor(goodsColorForms);
            goodsService.insertGoods(goodsForm);
        } catch (CosmeticException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteGoodsByGoodsIDTest(){
        try {
            goodsService.deleteGoodsByGoodsID("151781764404657");
        } catch (CosmeticException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void selectGoodsByNameTest(){
        BizResult<List<GoodsVO>> bizResult = goodsService.selectGoodsByName("活力劲肤露", 1, 4);
       Assert.notNull(bizResult, "不空");
    }

    @Test
    public void selectGoodsByBrandTest(){
        BizResult<List<GoodsVO>> bizResult = goodsService.selectGoodsByBrand("妮维雅", 1, 2);
        Assert.notNull(bizResult, "不空");
    }

    @Test
    public void selectAllGoods(){
        BizResult<List<GoodsVO>> bizResult = goodsService.selectAllGoods(1, 4);
        Assert.notNull(bizResult, "不空");
    }

    @Test
    public void selectGoodsByGoodsIdTest(){
        GoodsVO goodsVO = goodsService.selectGoodsByGoodsId("151782386398877");
        Assert.notNull(goodsVO, "不空");
    }

    @Test
    public void updateGoodsByGoodsIdTest(){
        GoodsRequest goodsForm = new GoodsRequest();
        goodsForm.setColor(1);
        goodsForm.setGoodsId("151781764404657");
        goodsForm.setBrandId(6L);
        goodsForm.setInventory(10000000l);
        goodsForm.setPrice(100000l);
        goodsForm.setSellPoint("乳液面霜 补水保湿 活力抗倦 男士化妆品");
        goodsForm.setWeight(500000);
        goodsForm.setSupplyPrice(80l);
        goodsForm.setShopName("妮维雅(NIVEA)男士Q10活力劲肤露50g（乳液面霜 补水保湿 活力抗倦 男士化妆品）");
        goodsForm.setShortName("妮维雅(NIVEA)男士Q10活力劲肤露50g");
        goodsForm.setMainPic("890");
        goodsForm.setHeadPic("7bn543");
        List<String> detailPics = Lists.newArrayList();
        detailPics.add("tre");
        detailPics.add("890");
        //goodsForm.setDetailPic(detailPics);
        List<GoodsColorForm> goodsColorForms = Lists.newArrayList();
        GoodsColorForm goodsColorForm1 = new GoodsColorForm();
        GoodsColorForm goodsColorForm2 = new GoodsColorForm();
        goodsColorForm1.setColorName("白色");
        goodsColorForm1.setInventory(89L);
        goodsColorForm2.setColorName("红色");
        goodsColorForm2.setInventory(89L);
        goodsColorForms.add(goodsColorForm1);
        goodsColorForms.add(goodsColorForm2);
        //goodsForm.setGoodsColor(goodsColorForms);
        goodsService.updateGoodsByGoodsId(goodsForm);
    }

    @Test
    public void selectGoodsByConditionsTest(){
        GoodsRequest goodsForm = new GoodsRequest();
        goodsForm.setShortName("妮维雅");
        //goodsForm.setGoodsId("151782386398877");
        goodsForm.setBrandId(6L);
        goodsForm.setIsDeleted(0);
        goodsForm.setPageNo(1);
        goodsForm.setPageSize(5);
        BizResult bizResult = goodsService.selectGoodsByConditions(goodsForm);
        System.out.println();
    }

}

