package com.cos.controller;

import com.alibaba.fastjson.JSON;
import com.cos.common.entity.BizResult;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.form.MeetingPlaceForm;
import com.cos.common.pojo.vo.MeetingPlaceVO;
import com.cos.service.intf.MeetingPlaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 会场controller
 * @author: hzf
 * @date: 2018/2/7 下午2:45
 */
@Controller
@RequestMapping("meetingPlace")
@Slf4j
public class MeetingPlaceController {

    @Autowired
    private MeetingPlaceService meetingPlaceService;

    /**
     * 查询所有会场信息
     * @param form
     * @return
     */
    @RequestMapping("selectMeetingPlaceByConditions")
    @ResponseBody
    public BizResult selectMeetingPlaceByConditions(@RequestBody MeetingPlaceForm form) {

        try {
            BizResult bizResult = meetingPlaceService.selectMeetingPlaceByConditions(form);
            return bizResult;
        } catch (CosmeticException e) {
            log.error("查询所有会场信息失败", e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }

    /**
     * 根据id查询会场信
     * @param id
     * @return
     */
    @RequestMapping("selectMeetingPlaceById")
    @ResponseBody
    public BizResult selectMeetingPlaceById(@RequestParam(value = "id") Long id) {
        try {
            MeetingPlaceVO meetingPlaceVO = meetingPlaceService.selectMeetingPlaceById(id);
            return BizResult.create(meetingPlaceVO);
        } catch (CosmeticException e) {
            log.error("根据id查询会场信息失败 id is {}", id, e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }

    /**
     * 新增会场
     * @param meetingPlaceForm
     * @return
     */
    @RequestMapping("insertMeetingPlace")
    @ResponseBody
    public BizResult insertMeetingPlace(@RequestBody MeetingPlaceForm meetingPlaceForm) {
        try {
            return meetingPlaceService.insertMeetingPlace(meetingPlaceForm);
        } catch (CosmeticException e) {
            log.error("新增会场失败 meetingPlaceForm is {}", JSON.toJSONString(meetingPlaceForm), e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }

    /**
     * 根据Id删除会场信息
     * @param id
     * @return
     */
    @RequestMapping("deleteMeetingPlaceById")
    @ResponseBody
    public BizResult deleteMeetingPlaceById( @RequestParam(value = "id") Long id ) {
        try {
            meetingPlaceService.deleteMeetingPlaceById(id);
            BizResult bizResult = new BizResult();
            bizResult.setSuccess(true);
            return bizResult;
        } catch (CosmeticException e) {
            log.error("新增会场失败 id is {}", id, e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }

    /**
     * 根据id修改会场信息
     * @param meetingPlaceForm
     * @return
     */
    @RequestMapping("updateMeetingPlaceById")
    @ResponseBody
    public BizResult updateMeetingPlaceById(@RequestBody MeetingPlaceForm meetingPlaceForm) {

        try {
            meetingPlaceService.updateMeetingPlaceById(meetingPlaceForm);
            BizResult bizResult = new BizResult();
            bizResult.setSuccess(true);
            return bizResult;
        } catch (CosmeticException e) {
            log.error("根据id更新会场失败 meetingPlaceForm is {}", JSON.toJSONString(meetingPlaceForm), e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }

    /**
     * 上架或者下架会场
     * @param meetingPlaceForm
     * @return
     */
    @RequestMapping("dropOrReleaseMeetingPlace")
    @ResponseBody
    public BizResult dropOrReleaseMeetingPlace(@RequestBody MeetingPlaceForm meetingPlaceForm) {

        try {
            meetingPlaceService.dropOrReleaseMeetingPlace(meetingPlaceForm);
            BizResult bizResult = new BizResult();
            bizResult.setSuccess(true);
            return bizResult;
        } catch (CosmeticException e) {
            log.error("根据id更新会场失败 meetingPlaceForm is {}", JSON.toJSONString(meetingPlaceForm), e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }
}
