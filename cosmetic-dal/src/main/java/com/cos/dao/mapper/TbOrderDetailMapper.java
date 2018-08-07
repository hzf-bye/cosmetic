package com.cos.dao.mapper;

import com.cos.dao.pojo.TbOrderDetail;
import com.cos.dao.pojo.TbOrderDetailExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbOrderDetailMapper {
    int countByExample(TbOrderDetailExample example);

    int deleteByExample(TbOrderDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbOrderDetail record);

    int insertSelective(TbOrderDetail record);

    List<TbOrderDetail> selectByExample(TbOrderDetailExample example);

    TbOrderDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbOrderDetail record, @Param("example") TbOrderDetailExample example);

    int updateByExample(@Param("record") TbOrderDetail record, @Param("example") TbOrderDetailExample example);

    int updateByPrimaryKeySelective(TbOrderDetail record);

    int updateByPrimaryKey(TbOrderDetail record);
}