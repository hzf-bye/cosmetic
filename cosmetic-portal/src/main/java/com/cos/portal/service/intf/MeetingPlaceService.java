package com.cos.portal.service.intf;

import com.cos.common.entity.BizResult;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.vo.MeetingPlaceVO;

import java.util.List;

/**
 * @description: 会场service
 * @author: hzf
 * @date: 2018/2/7 上午10:56
 */
public interface MeetingPlaceService {

    /**
     * 查询当前进行中的会场
     * @param pageNo
     * @param pageSize
     * @return
     * @throws CosmeticException
     */
    BizResult<List<MeetingPlaceVO>> selectMeetingPlace(Integer pageNo, Integer pageSize) throws CosmeticException;


}
