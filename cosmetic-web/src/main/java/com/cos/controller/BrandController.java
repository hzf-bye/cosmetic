package com.cos.controller;

import com.alibaba.fastjson.JSONObject;
import com.cos.common.entity.BizResult;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.vo.BrandVO;
import com.cos.dao.mapper.ext.TbBrandExtMapper;
import com.cos.service.intf.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

/**
 * @description: 品牌Controller
 * @author: hzf
 * @date: 2018/2/6 下午6:53
 */
@Controller
@Slf4j
@RequestMapping("/brand")
public class
BrandController {

    @Autowired
    private BrandService brandService;

    //private Logger log = LoggerFactory.getLogger(BrandController.class);

    /**
     * 新增品牌
     * @param brandName
     * @return
     */
    @RequestMapping("/insertBrand")
    @ResponseBody
    public  BizResult insertBrand(@RequestParam (value = "brandName") String brandName){
        try {
            brandService.insertBrand(brandName);
            BizResult bizResult = new BizResult();
            bizResult.setSuccess(true);
            return bizResult;
        } catch (CosmeticException e) {
            log.error("新增品牌错误,name is {}",brandName, e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }

    /**
     * 根据品牌id删除品牌信息
     * @param id
     */
    @RequestMapping("/deleteBrandById")
    @ResponseBody
    public BizResult deleteBrandById(@RequestParam(value = "id") Long id) {
        try {
            brandService.deleteBrandById(id);
            BizResult bizResult = new BizResult();
            bizResult.setSuccess(true);
            return bizResult;
        } catch (CosmeticException e) {
            log.error("根据id删除品牌错误,id is {}",id, e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }

    /**
     * 根据品牌名逻辑删除品牌信息
     * @param brandName
     */
    @RequestMapping("/deleteBrandByName")
    @ResponseBody
    public BizResult deleteBrandByName(@RequestParam(value = "brandName") String brandName) {
        try {
            brandService.deleteBrandByName(brandName);
            BizResult bizResult = new BizResult();
            bizResult.setSuccess(true);
            return bizResult;
        } catch (CosmeticException e) {
            log.error("根据品牌名删除品牌错误,brandName is {}",brandName, e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }
    /**
     * 根据品牌id更新品牌信息
     * @param id
     * @param isDelete
     */
    @RequestMapping("/updateBrandById")
    @ResponseBody
    public BizResult updateBrandById(@RequestParam(value = "id") Long id,
                                     @RequestParam(value = "isDeleted") Integer isDeleted) {
        try {
            brandService.updateBrandById(id, isDeleted);
            BizResult bizResult = new BizResult();
            bizResult.setSuccess(true);
            return bizResult;
        } catch (CosmeticException e) {
            log.error("根据品牌id更新品牌错误,id is {}",id, e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }

    /**
     * 根据品牌id查询品牌
     * @param id
     * @return
     * @throws CosmeticException
     */
    @RequestMapping("/selectBrandById")
    @ResponseBody
    public BizResult selectBrandById(@RequestParam(value = "id") Long id) {

        try {
            BrandVO brandVO = brandService.selectBrandById(id);

            return BizResult.create(brandVO);
        } catch (CosmeticException e) {
            log.error("根据品牌id查询品牌错误,id is {}",id, e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }
    /**
     * 根据名称模糊查询品牌名
     * @param name
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping("/selectBrandByName")
    @ResponseBody
    public BizResult selectBrandByName(@RequestParam(value = "name") String name,
                                       @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){

        try {
            BizResult bizResult = brandService.selectBrandByName(name, pageNo, pageSize);
            return bizResult;
        } catch (CosmeticException e) {
            log.error("根据品牌名模糊查询品牌错误,name is {}",name, e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }

    /**
     * 查询条件查询所有品牌(有名称则名称模糊查询，无名称则查询所有品牌) 分页查询
     * @return
     */
    @RequestMapping("/selectAllBrand")
    @ResponseBody
    public BizResult selectAllBrand(@RequestParam(value = "name", required = false) String name,
                                    @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        try {
            BizResult bizResult = brandService.selectBrandByName(name, pageNo, pageSize);
            return bizResult;
        } catch (CosmeticException e) {
            log.error("查询所有品牌错误", e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }


}
