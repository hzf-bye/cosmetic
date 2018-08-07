package com.cos.service.intf;

import com.cos.common.entity.BizResult;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.form.MeetingPlaceForm;
import com.cos.common.pojo.vo.MeetingPlaceVO;

import java.util.List;

/**
 * @description: 会场service
 * @author: hzf
 * @date: 2018/2/7 上午10:56
 */
public interface MeetingPlaceService {

    /**
     * 查询所有会场信息
     * @param pageNo
     * @param pageSize
     * @return
     * @throws CosmeticException
     */
    BizResult<List<MeetingPlaceVO>> selectAllMeetingPlace(Integer pageNo, Integer pageSize) throws CosmeticException;

    /**
     * 新增会场
     * @param meetingPlaceForm
     * @throws CosmeticException
     */
    BizResult<Long> insertMeetingPlace(MeetingPlaceForm meetingPlaceForm) throws CosmeticException;

    /**
     * 根据id查询会场
     * @param id
     * @return
     * @throws CosmeticException
     */
    MeetingPlaceVO selectMeetingPlaceById(Long id) throws CosmeticException;

    /**
     * 根据id删除会场
     * @param id
     * @throws CosmeticException
     */
    void deleteMeetingPlaceById(Long id) throws CosmeticException;

    /**
     * 根据id修改会场信息
     * @param meetingPlaceForm
     * @throws CosmeticException
     */
    void updateMeetingPlaceById(MeetingPlaceForm meetingPlaceForm) throws CosmeticException;

    /**
     * 上架or下架会场
     * @param meetingPlaceForm
     * @throws CosmeticException
     */
    void dropOrReleaseMeetingPlace(MeetingPlaceForm meetingPlaceForm) throws CosmeticException;

    /**
     * 根据条件查询会场信息
     * @return
     * @throws CosmeticException
     */
    BizResult<List<MeetingPlaceVO>> selectMeetingPlaceByConditions(MeetingPlaceForm meetingPlaceForm) throws CosmeticException;

}
