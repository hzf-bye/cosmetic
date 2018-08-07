package com.cos.rest.controller;

import com.cos.common.entity.BizResult;
import com.cos.common.exception.CosmeticException;
import com.cos.rest.service.intf.MeetingPlaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 会场controller
 * @author: hzf
 * @date: 2018/3/8 下午1:24
 */
@Controller
@Slf4j
@RequestMapping("restMeetingPlace")
public class MeetingPlaceController {


    @Autowired
    private MeetingPlaceService meetingPlaceService;

    /**
     * 查询会场信息
     * @return BizResult
     */
    @RequestMapping("selectMeetingPlace")
    @ResponseBody
    public BizResult selectMeetingPlace(@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                        @RequestParam(value = "pageSize", required = false, defaultValue = "1") Integer pageSize){

        try {
            BizResult bizResult = meetingPlaceService.selectMeetingPlace(pageNo, pageSize);
            return bizResult;
        } catch (CosmeticException e) {
            log.error("查询所有会场信息失败", e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }
}
