package service;

import base.BaseTest;
import com.cos.common.entity.BizResult;
import com.cos.common.pojo.form.GoodsForm;
import com.cos.portal.service.intf.GoodsService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description:
 * @author: hzf
 * @date: 2018/3/8 下午3:57
 */
public class GoodsServiceTest extends BaseTest{

    @Autowired
    private GoodsService goodsService;

    @Test
    public void selectGoodsByConditionsTest(){
        GoodsForm goodsForm = new GoodsForm();
        goodsForm.setBrandId(6L);
        goodsForm.setPageNo(1);
        goodsForm.setPageSize(3);
        BizResult bizResult = goodsService.selectGoodsByConditions(goodsForm);
        System.out.println(bizResult);
    }

    @Test
    public void selectGoodsByGoodsIdTest(){
        GoodsForm goodsForm = new GoodsForm();
        goodsForm.setGoodsId("151782386398877");
        BizResult bizResult = goodsService.selectGoodsByGoodsId(goodsForm);
        System.out.println(bizResult.getData());
    }
}
