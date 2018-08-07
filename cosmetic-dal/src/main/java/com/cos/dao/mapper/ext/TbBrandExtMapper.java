package com.cos.dao.mapper.ext;

import com.cos.dao.mapper.TbBrandMapper;

/**
 * @Description:
 * @author: hzf
 * @Date: 2018/5/8 下午2:34
 */
public interface TbBrandExtMapper extends TbBrandMapper {

    /**
     * 查询品牌对应的销量
     */
    Integer selectBrandSalesVolume(long brandId);
}
