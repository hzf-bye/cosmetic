package com.cos.service.impl;

import com.cos.common.entity.BizResult;
import com.cos.common.enums.CommonStatusEnum;
import com.cos.common.enums.ErrorCodeEnum;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.vo.BrandVO;
import com.cos.common.utils.BeanUtil;
import com.cos.dao.mapper.TbBrandMapper;
import com.cos.dao.mapper.ext.TbBrandExtMapper;
import com.cos.dao.pojo.TbBrand;
import com.cos.dao.pojo.TbBrandExample;
import com.cos.service.intf.BrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @description: 品牌service实现类
 * @author: hzf
 * @date: 2018/2/6 下午5:31
 */
@Slf4j
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    TbBrandExtMapper brandExtMapper;

    /**
     * 新增品牌
     *
     * @param brandName
     * @throws CosmeticException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertBrand(String brandName) throws CosmeticException {
        try {
            TbBrandExample brandExample = new TbBrandExample();
            brandExample.createCriteria().andBrandNameEqualTo(brandName);
            List<TbBrand> tbBrands = brandExtMapper.selectByExample(brandExample);
            if ( !CollectionUtils.isEmpty(tbBrands) ) {
                log.error("数据库中已存在该品牌名 brandName is {} ", brandName);
                throw new CosmeticException("1008", "已存在该品牌名");
            }
            TbBrand tbBrand = new TbBrand();
            tbBrand.setBrandName(brandName);
            tbBrand.setGmtCreat(new Date());
            tbBrand.setGmtModified(new Date());
            brandExtMapper.insertSelective(tbBrand);
            throw new CosmeticException(ErrorCodeEnum.EXCEPTION);
        } catch (CosmeticException e) {
            throw e;
        } catch (Exception e) {
            throw new CosmeticException(ErrorCodeEnum.EXCEPTION);
        }

    }

    /**
     * 根据品牌id逻辑删除品牌信息
     *
     * @param id
     * @throws CosmeticException
     */
    @Override
    public void deleteBrandById(Long id) throws CosmeticException {
        try {

            TbBrandExample brandExample = new TbBrandExample();
            brandExample.createCriteria().andIdEqualTo(id);
            int count = brandExtMapper.deleteByExample(brandExample);
            if ( count == 0 ) {
                throw new CosmeticException(ErrorCodeEnum.DATA_ERROR);
            }
        } catch (CosmeticException e) {
            throw e;
        } catch (Exception e) {
            throw new CosmeticException(ErrorCodeEnum.EXCEPTION);
        }
    }

    /**
     * 根据品牌名删除品牌信息
     *
     * @param brandName
     * @throws CosmeticException
     */
    @Override
    public void deleteBrandByName(String brandName) throws CosmeticException {
        try {
            TbBrandExample brandExample = new TbBrandExample();
            brandExample.createCriteria().andBrandNameEqualTo(brandName);
            int count = brandExtMapper.deleteByExample(brandExample);
            if ( count == 0 ) {
                throw new CosmeticException(ErrorCodeEnum.DATA_ERROR);
            }
        } catch (CosmeticException e) {
            throw e;
        } catch (Exception e) {
            throw new CosmeticException(ErrorCodeEnum.EXCEPTION);
        }
    }

    /**
     * 根据品牌id更新品牌信息
     *
     * @param id
     * @param isDeleted
     * @throws CosmeticException
     */
    @Override
    public void updateBrandById(Long id, Integer isDeleted) throws CosmeticException {
        try {
            TbBrandExample brandExample = new TbBrandExample();
            brandExample.createCriteria().andIdEqualTo(id);
            TbBrand tbBrand = new TbBrand();
            tbBrand.setGmtModified(new Date());
            tbBrand.setIsDeleted(isDeleted);
            int count = brandExtMapper.updateByExampleSelective(tbBrand, brandExample);
            if ( count == 0 ) {
                throw new CosmeticException(ErrorCodeEnum.DATA_ERROR);
            }
        } catch (CosmeticException e) {
            throw e;
        } catch (Exception e) {
            throw new CosmeticException(ErrorCodeEnum.EXCEPTION);
        }

    }

    /**
     * 根据品牌id查询品牌
     *
     * @param id
     * @return
     * @throws CosmeticException
     */
    @Override
    public BrandVO selectBrandById(Long id) throws CosmeticException {
        try {
            TbBrand tbBrand = brandExtMapper.selectByPrimaryKey(id);
            if ( null == tbBrand ) {
                throw new CosmeticException(ErrorCodeEnum.NO_DATA);
            }
            BrandVO brandVO = BeanUtil.createCloneBean(tbBrand, BrandVO.class);
            return brandVO;
        } catch (CosmeticException e) {
            throw e;
        } catch (Exception e) {
            throw new CosmeticException(ErrorCodeEnum.EXCEPTION);
        }
    }

    /**
     * 根据名称模糊查询品牌名
     *
     * @param name
     * @param pageNo
     * @param pageSize
     * @return
     * @throws CosmeticException
     */
    @Override
    public BizResult<List<BrandVO>> selectBrandByName(String name, Integer pageNo, Integer pageSize) throws CosmeticException {
        try {
            BizResult<List<BrandVO>> bizResult = new BizResult();
            //分页处理
            PageHelper.startPage(pageNo, pageSize);
            TbBrandExample brandExample = new TbBrandExample();
            TbBrandExample.Criteria criteria = brandExample.createCriteria();
            if (StringUtils.isNotBlank(name)) {
                criteria.andBrandNameLike("%" + name + "%");
            }
            brandExample.setOrderByClause("gmt_modified desc");
            List<TbBrand> tbBrands = brandExtMapper.selectByExample(brandExample);
            if ( CollectionUtils.isEmpty(tbBrands) ) {
                throw new CosmeticException(ErrorCodeEnum.NO_DATA);
            }
            //取total
            PageInfo<TbBrand> pageInfo = new PageInfo<>(tbBrands);
            long total = pageInfo.getTotal();
            List<BrandVO> brandVOS = tbBrands.stream().map(tbBrand -> {
                return BeanUtil.createCloneBean(tbBrand, BrandVO.class);
            }).collect(Collectors.toList());
            //遍历品牌取出对应的销量
            brandVOS.forEach(brandVO -> {
                Integer salesVolume = brandExtMapper.selectBrandSalesVolume(brandVO.getId());
                brandVO.setSalesVolume(salesVolume == null ? 0 : salesVolume);

            });
            bizResult.setData(brandVOS);
            bizResult.setSuccess(true);
            bizResult.setPageNo(pageNo);
            bizResult.setPageSize(pageSize);
            bizResult.setTotalCount((int)total);
            return bizResult;
        } catch (CosmeticException e) {
            throw e;
        } catch (Exception e) {
            throw new CosmeticException(ErrorCodeEnum.EXCEPTION);
        }
    }

    /**
     * 查询所有品牌
     * @param pageNo
     * @param pageSize
     * @return
     * @throws CosmeticException
     */
    @Override
    public BizResult<List<BrandVO>> selectAllBrand(Integer pageNo, Integer pageSize) throws CosmeticException {
        try {
            BizResult<List<BrandVO>> bizResult = new BizResult();
            //分页处理
            PageHelper.startPage(pageNo, pageSize);
            TbBrandExample brandExample = new TbBrandExample();
            brandExample.setOrderByClause("gmt_modified desc");
            List<TbBrand> tbBrands = brandExtMapper.selectByExample(brandExample);
            if ( CollectionUtils.isEmpty(tbBrands) ) {
                throw new CosmeticException(ErrorCodeEnum.NO_DATA);
            }
            //取total
            PageInfo<TbBrand> pageInfo = new PageInfo<>(tbBrands);
            long total = pageInfo.getTotal();
            List<BrandVO> brandVOS = Lists.transform(tbBrands, new Function<TbBrand, BrandVO>() {
                @Override
                public BrandVO apply(TbBrand tbBrand) {
                    BrandVO brandVO = BeanUtil.createCloneBean(tbBrand, BrandVO.class);
                    return brandVO;
                }
            });
            //遍历品牌取出对应的销量
            brandVOS.forEach(brandVO -> {
                Integer salesVolume = brandExtMapper.selectBrandSalesVolume(brandVO.getId());
                brandVO.setSalesVolume(salesVolume);

            });
            bizResult.setData(brandVOS);
            bizResult.setSuccess(true);
            bizResult.setPageNo(pageNo);
            bizResult.setPageSize(pageSize);
            bizResult.setTotalCount((int)total);
            return bizResult;
        } catch (CosmeticException e) {
            throw e;
        } catch (Exception e) {
            throw new CosmeticException(ErrorCodeEnum.EXCEPTION);
        }
    }
}
