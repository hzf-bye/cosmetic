package com.cos.dao.mapper;

import com.cos.dao.pojo.TbMeetingPlace;
import com.cos.dao.pojo.TbMeetingPlaceExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbMeetingPlaceMapper {
    int countByExample(TbMeetingPlaceExample example);

    int deleteByExample(TbMeetingPlaceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbMeetingPlace record);

    int insertSelective(TbMeetingPlace record);

    List<TbMeetingPlace> selectByExample(TbMeetingPlaceExample example);

    TbMeetingPlace selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbMeetingPlace record, @Param("example") TbMeetingPlaceExample example);

    int updateByExample(@Param("record") TbMeetingPlace record, @Param("example") TbMeetingPlaceExample example);

    int updateByPrimaryKeySelective(TbMeetingPlace record);

    int updateByPrimaryKey(TbMeetingPlace record);
}