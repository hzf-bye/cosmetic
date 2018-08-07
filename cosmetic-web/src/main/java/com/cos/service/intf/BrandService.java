package com.cos.service.intf;

import com.cos.common.entity.BizResult;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.vo.BrandVO;

import java.util.List;

/**
 * @description: 品牌service
 * @author: hzf
 * @date: 2018/2/6 下午5:23
 */

public interface BrandService {

    /**
     * 新增品牌
     * @param brandName
     * @throws CosmeticException
     */
    void insertBrand(String brandName) throws CosmeticException;

    /**
     * 根据品牌id逻辑删除品牌信息
     * @param id
     * @throws CosmeticException
     */
    void deleteBrandById(Long id) throws CosmeticException;

    /**
     * 根据品牌名删除品牌信息
     * @param brandName
     * @throws CosmeticException
     */
    void deleteBrandByName(String brandName) throws CosmeticException;

    /**
     * 根据品牌id更新品牌信息
     * @param id
     * @param isDeleted
     * @throws CosmeticException
     */
    void updateBrandById(Long id, Integer isDeleted) throws CosmeticException;

    /**
     * 根据品牌id查询品牌
     * @param id
     * @return
     * @throws CosmeticException
     */
    BrandVO selectBrandById(Long id) throws CosmeticException;

    /**
     * 根据名称模糊查询品牌名
     * @param name
     * @param pageNo
     * @param pageSize
     * @return
     * @throws CosmeticException
     */
    BizResult<List<BrandVO>> selectBrandByName(String name, Integer pageNo, Integer pageSize) throws CosmeticException;

    /**
     * 查询所有品牌
     * @param pageNo
     * @param pageSize
     * @return
     * @throws CosmeticException
     */
    BizResult<List<BrandVO>> selectAllBrand(Integer pageNo, Integer pageSize) throws CosmeticException;
}
