package com.cos.portal.service.impl;

import com.alibaba.fastjson.JSON;
import com.cos.common.entity.BizResult;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.vo.MeetingPlaceVO;
import com.cos.common.utils.HttpClientUtil;
import com.cos.common.utils.HttpClientUtils;
import com.cos.portal.service.intf.MeetingPlaceService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @description: 会场service实现类
 * @author: hzf
 * @date: 2018/3/8 下午1:56
 */
@Service
@Slf4j
public class MeetingPlaceServiceImpl implements MeetingPlaceService{

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Value("${SELECT_MEETING_PLACE}")
    private String SELECT_MEETING_PLACE;

    /**
     * 查询当前进行中的会场
     *
     * @param pageNo
     * @param pageSize
     * @return
     * @throws CosmeticException
     */
    @Override
    public BizResult<List<MeetingPlaceVO>> selectMeetingPlace(Integer pageNo, Integer pageSize) throws CosmeticException {

        Map<String, String> params = Maps.newHashMap();
        params.put("pageNo", pageNo + "");
        params.put("pageSize", pageSize + "");

        try {
            //调用服务
            String json = HttpClientUtils.doPost(REST_BASE_URL + SELECT_MEETING_PLACE, params);
            BizResult result = JSON.parseObject(json, BizResult.class);
            return result;
        } catch (Exception e) {
            log.error("调用服务层查询会场接口失败", e);
            return BizResult.create("9999", e.getMessage());
        }
    }
}
