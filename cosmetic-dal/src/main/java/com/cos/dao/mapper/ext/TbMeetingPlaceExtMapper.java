package com.cos.dao.mapper.ext;

import com.cos.common.pojo.form.MeetingPlaceForm;
import com.cos.common.pojo.vo.MeetingPlaceVO;
import com.cos.dao.mapper.TbMeetingPlaceMapper;

import java.util.List;

/**
 * @description:
 * @author: hzf
 * @date: 2018/2/7 上午11:13
 */
public interface TbMeetingPlaceExtMapper extends TbMeetingPlaceMapper {

    List<MeetingPlaceVO> selectAllMeetingPlace();

    List<MeetingPlaceVO> selectMeetingPlaceByConditions(MeetingPlaceForm meetingPlaceForm);

}
