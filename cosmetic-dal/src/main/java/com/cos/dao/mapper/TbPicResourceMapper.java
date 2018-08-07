package com.cos.dao.mapper;

import com.cos.dao.pojo.TbPicResource;
import com.cos.dao.pojo.TbPicResourceExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbPicResourceMapper {
    int countByExample(TbPicResourceExample example);

    int deleteByExample(TbPicResourceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbPicResource record);

    int insertSelective(TbPicResource record);

    List<TbPicResource> selectByExample(TbPicResourceExample example);

    TbPicResource selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbPicResource record, @Param("example") TbPicResourceExample example);

    int updateByExample(@Param("record") TbPicResource record, @Param("example") TbPicResourceExample example);

    int updateByPrimaryKeySelective(TbPicResource record);

    int updateByPrimaryKey(TbPicResource record);
}