package com.cos.service;

import com.cos.base.BaseTest;
import com.cos.common.entity.BizResult;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.vo.BrandVO;
import com.cos.service.intf.BrandService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @description: 品牌service测试类
 * @author: hzf
 * @date: 2018/2/6 下午5:52
 */
public class BrandServiceTest extends BaseTest {

    @Autowired
    private BrandService brandService;

    @Test
    public void insertBrandTest(){

        try {
            brandService.insertBrand("福1");
        } catch (CosmeticException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteBrandByIdTest(){

        try {
            brandService.deleteBrandById(11L);
        } catch (CosmeticException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteBrandByNameTest(){

        try {
            brandService.deleteBrandByName("福");
        } catch (CosmeticException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateBrandByIdTest(){

        try {
            brandService.updateBrandById(14L, 1);
        } catch (CosmeticException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void selectBrandByIdTest(){
        try {
            BrandVO brandVO = brandService.selectBrandById(15L);
            System.out.println();
        } catch (CosmeticException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void selectBrandByNameTest(){
        try {
            BizResult<List<BrandVO>> bizResult = brandService.selectBrandByName("福", 1, 1);
            System.out.println();
        } catch (CosmeticException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void selectAllBrandTest(){
        try {
            BizResult<List<BrandVO>>  bizResult = brandService.selectAllBrand(1, 4);
            System.out.println();
        } catch (CosmeticException e) {
            e.printStackTrace();
        }
    }
}
