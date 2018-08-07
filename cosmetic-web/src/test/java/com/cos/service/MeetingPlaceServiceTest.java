package com.cos.service;

import com.cos.base.BaseTest;
import com.cos.common.entity.BizResult;
import com.cos.common.pojo.form.MeetingPlaceForm;
import com.cos.common.pojo.vo.MeetingPlaceVO;
import com.cos.service.intf.MeetingPlaceService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @description: 会场service测试类
 * @author: hzf
 * @date: 2018/2/7 下午2:27
 */
public class MeetingPlaceServiceTest extends BaseTest {

    @Autowired
    private MeetingPlaceService meetingPlaceService;

    @Test
    public void selectAllMeetingPlaceTest(){

        BizResult<List<MeetingPlaceVO>> bizResult = meetingPlaceService.selectAllMeetingPlace(1, 2);
        System.out.println();
    }

    @Test
    public void insertMeetingPlaceTest(){
        MeetingPlaceForm meetingPlaceForm = new MeetingPlaceForm();
        meetingPlaceForm.setBrandName("百雀羚");
        meetingPlaceForm.setName("百雀羚品牌日");
        meetingPlaceForm.setEndTime(4567890L);
        meetingPlaceForm.setStartTime(23456L);
        meetingPlaceService.insertMeetingPlace(meetingPlaceForm);
    }

    @Test
    public void selectMeetingPlaceByIdTest(){
        MeetingPlaceVO meetingPlaceVO = meetingPlaceService.selectMeetingPlaceById(1L);
        System.out.println();
    }

    @Test
    public void selectMeetingPlaceByConditionsTest(){
        MeetingPlaceForm meetingPlaceForm = new MeetingPlaceForm();
        meetingPlaceForm.setPageNo(1);
        meetingPlaceForm.setPageSize(10);
        BizResult<List<MeetingPlaceVO>> bizResult = meetingPlaceService.selectMeetingPlaceByConditions(meetingPlaceForm);
        System.out.println();
    }

    @Test
    public void deleteMeetingPlaceByIdTest(){
        meetingPlaceService.deleteMeetingPlaceById(3L);
    }

    @Test
    public void updateMeetingPlaceByIdTest(){
        MeetingPlaceForm meetingPlaceForm = new MeetingPlaceForm();
        meetingPlaceForm.setBrandName("百雀羚");
        meetingPlaceForm.setName("百雀羚品牌日");
        meetingPlaceForm.setEndTime(4567890L);
        meetingPlaceForm.setStartTime(23456L);
        meetingPlaceForm.setId(3L);
        meetingPlaceService.updateMeetingPlaceById(meetingPlaceForm );
    }

}
