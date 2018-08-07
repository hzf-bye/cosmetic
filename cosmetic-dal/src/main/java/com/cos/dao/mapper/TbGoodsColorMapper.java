package com.cos.dao.mapper;

import com.cos.dao.pojo.TbGoodsColor;
import com.cos.dao.pojo.TbGoodsColorExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbGoodsColorMapper {
    int countByExample(TbGoodsColorExample example);

    int deleteByExample(TbGoodsColorExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbGoodsColor record);

    int insertSelective(TbGoodsColor record);

    List<TbGoodsColor> selectByExample(TbGoodsColorExample example);

    TbGoodsColor selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbGoodsColor record, @Param("example") TbGoodsColorExample example);

    int updateByExample(@Param("record") TbGoodsColor record, @Param("example") TbGoodsColorExample example);

    int updateByPrimaryKeySelective(TbGoodsColor record);

    int updateByPrimaryKey(TbGoodsColor record);
}