package com.cos.service.impl;

import com.cos.common.entity.BizResult;
import com.cos.common.enums.CommonStatusEnum;
import com.cos.common.enums.ErrorCodeEnum;
import com.cos.common.enums.MeetingPlaceStatusEnum;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.form.MeetingPlaceForm;
import com.cos.common.pojo.vo.MeetingPlaceVO;
import com.cos.common.utils.BeanUtil;
import com.cos.dao.mapper.TbBrandMapper;
import com.cos.dao.mapper.ext.TbBrandExtMapper;
import com.cos.dao.mapper.ext.TbMeetingPlaceExtMapper;
import com.cos.dao.pojo.TbBrand;
import com.cos.dao.pojo.TbBrandExample;
import com.cos.dao.pojo.TbMeetingPlace;
import com.cos.dao.pojo.TbMeetingPlaceExample;
import com.cos.service.intf.MeetingPlaceService;
import com.cos.service.intf.BrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @description: 会场service实现类
 * @author: hzf
 * @date: 2018/2/7 上午11:22
 */
@Service
@Slf4j
public class MeetingPlaceServiceImpl implements MeetingPlaceService {

    @Autowired
    private TbMeetingPlaceExtMapper meetingPlaceExtMapper;
    @Autowired
    private TbBrandExtMapper brandMapper;
    @Autowired
    private BrandService brandService;


    /**
     * 查询所有会场信息
     *
     * @param pageNo
     * @param pageSize
     * @return
     * @throws CosmeticException
     */
    @Override
    public BizResult<List<MeetingPlaceVO>> selectAllMeetingPlace(Integer pageNo, Integer pageSize) throws CosmeticException {
        try {
            BizResult<List<MeetingPlaceVO>> bizResult = new BizResult<>();
            //分页处理
            PageHelper.startPage(pageNo, pageSize);
            List<MeetingPlaceVO> meetingPlaceVOS = meetingPlaceExtMapper.selectAllMeetingPlace();
            if ( CollectionUtils.isEmpty(meetingPlaceVOS )) {
                throw new CosmeticException(ErrorCodeEnum.NO_DATA);
            }


            for (MeetingPlaceVO meetingPlaceVO : meetingPlaceVOS) {
                Integer status = meetingPlaceVO.getStatus();
                long nowTime = System.currentTimeMillis();
                if (nowTime < meetingPlaceVO.getStartTime() &&
                        (meetingPlaceVO.getStatus().compareTo(MeetingPlaceStatusEnum.UNPUBLISHED.getCode()) != 0) ) {
                    //判断此事时间还未到会场开始时间且会场不处于未发布状态，则设置会场状态为未开始
                    status = MeetingPlaceStatusEnum.NO_START.getCode();
                } else if ( nowTime >= meetingPlaceVO.getStartTime() && nowTime <= meetingPlaceVO.getEndTime() &&
                        (meetingPlaceVO.getStatus().compareTo(MeetingPlaceStatusEnum.UNPUBLISHED.getCode()) != 0)) {
                    //判断此时时间为会场开始时间与结束时间之间且会场不处于未发布状态，则设置会场状态为进行中
                    status = MeetingPlaceStatusEnum.PROCESSING.getCode();
                } else if (nowTime > meetingPlaceVO.getEndTime() &&
                        (meetingPlaceVO.getStatus().compareTo(MeetingPlaceStatusEnum.UNPUBLISHED.getCode()) != 0) ) {
                    //判断此时时间已过会场结束时间且会场不处于未发布状态，则设置会场状态为已结束
                    status = MeetingPlaceStatusEnum.OVER.getCode();
                }
                meetingPlaceVO.setStatus(status);
                //将此时的会场状态写会数据库
                MeetingPlaceForm meetingPlaceForm = new MeetingPlaceForm();
                meetingPlaceForm.setId(meetingPlaceVO.getId());
                meetingPlaceForm.setStatus(status);
                this.updateMeetingPlaceById(meetingPlaceForm);
            }

            PageInfo<MeetingPlaceVO> pageInfo = new PageInfo<>(meetingPlaceVOS);
            long total = pageInfo.getTotal();

            bizResult.setTotalCount((int)total);
            bizResult.setPageSize(pageSize);
            bizResult.setPageNo(pageNo);
            bizResult.setData(meetingPlaceVOS);
            bizResult.setSuccess(true);
            return bizResult;
        } catch (CosmeticException e) {
            throw e;
        } catch (Exception e) {
            throw new CosmeticException(ErrorCodeEnum.EXCEPTION);
        }
    }

    /**
     * 新增会场
     *
     * @param meetingPlaceForm
     * @throws CosmeticException
     */
    @Override
    public BizResult<Long> insertMeetingPlace(MeetingPlaceForm meetingPlaceForm) throws CosmeticException {

        try {

            //新增会场
            TbMeetingPlace tbMeetingPlace = new TbMeetingPlace();
            tbMeetingPlace.setBrandId(meetingPlaceForm.getBrandId());
            tbMeetingPlace.setName(meetingPlaceForm.getName());
            tbMeetingPlace.setGmtCreat(new Date());
            tbMeetingPlace.setGmtModified(new Date());
            tbMeetingPlace.setStartTime(meetingPlaceForm.getStartTime());
            tbMeetingPlace.setEndTime(meetingPlaceForm.getEndTime());
            meetingPlaceExtMapper.insertSelective(tbMeetingPlace);
            return BizResult.create(tbMeetingPlace.getId());
        } catch (Exception e) {
            throw new CosmeticException(ErrorCodeEnum.INSERT_FAIL);
        }

    }

    /**
     * 根据id查询会场
     *
     * @param id
     * @return
     * @throws CosmeticException
     */
    @Override
    public MeetingPlaceVO selectMeetingPlaceById(Long id) throws CosmeticException {

        try {
            TbMeetingPlace tbMeetingPlace = meetingPlaceExtMapper.selectByPrimaryKey(id);
            if ( null == tbMeetingPlace ) {
                throw new CosmeticException(ErrorCodeEnum.NO_DATA);
            }
            MeetingPlaceVO meetingPlaceVO = BeanUtil.createCloneBean(tbMeetingPlace, MeetingPlaceVO.class);
            return meetingPlaceVO;
        } catch (CosmeticException e) {
            throw e;
        } catch (Exception e) {
            throw new CosmeticException(ErrorCodeEnum.EXCEPTION);
        }
    }

    /**
     * 根据id删除会场
     *
     * @param id
     * @throws CosmeticException
     */
    @Override
    public void deleteMeetingPlaceById(Long id) throws CosmeticException {
        try {
            int count = meetingPlaceExtMapper.deleteByPrimaryKey(id);
            if ( count == 0) {
                throw new CosmeticException(ErrorCodeEnum.DATA_ERROR);
            }
        } catch (CosmeticException e) {
            throw e;
        } catch (Exception e) {
            throw new CosmeticException(ErrorCodeEnum.EXCEPTION);
        }
    }

    /**
     * 根据id修改会场信息
     *
     * @param meetingPlaceForm
     * @throws CosmeticException
     */
    @Override
    public void updateMeetingPlaceById(MeetingPlaceForm meetingPlaceForm) throws CosmeticException {

        try {
            TbMeetingPlace tbMeetingPlace = BeanUtil.createCloneBean(meetingPlaceForm, TbMeetingPlace.class);
            tbMeetingPlace.setGmtModified(new Date());
            int count = meetingPlaceExtMapper.updateByPrimaryKeySelective(tbMeetingPlace);
            if ( count == 0) {
                throw new CosmeticException(ErrorCodeEnum.DATA_ERROR);
            }
        } catch (CosmeticException e) {
            throw e;
        } catch (Exception e) {
            throw new CosmeticException(ErrorCodeEnum.EXCEPTION);
        }
    }

    /**
     * 上架or下架会场
     *
     * @param meetingPlaceForm
     * @throws CosmeticException
     */
    @Override
    public void dropOrReleaseMeetingPlace(MeetingPlaceForm meetingPlaceForm) throws CosmeticException {

        try {
            //根据id查询会场会场信息
            TbMeetingPlace tbMeetingPlace = meetingPlaceExtMapper.selectByPrimaryKey(meetingPlaceForm.getId());
            //如果是上架判断当前上架会场是否与正在进行的会场冲突
            if (meetingPlaceForm.getStatus().equals(2)) {
                TbMeetingPlaceExample example = new TbMeetingPlaceExample();
                TbMeetingPlaceExample.Criteria criteria1 = example.or();
                criteria1.andStartTimeLessThanOrEqualTo(tbMeetingPlace.getEndTime())
                        .andStartTimeGreaterThanOrEqualTo(tbMeetingPlace.getStartTime())
                        .andStatusNotIn(Lists.newArrayList(1, 4));
                TbMeetingPlaceExample.Criteria criteria2 = example.or();
                criteria2.andStartTimeLessThanOrEqualTo(tbMeetingPlace.getStartTime())
                        .andEndTimeGreaterThanOrEqualTo(tbMeetingPlace.getEndTime())
                        .andStatusNotIn(Lists.newArrayList(1, 4));
                TbMeetingPlaceExample.Criteria criteria3 = example.or();
                criteria3.andEndTimeGreaterThanOrEqualTo(tbMeetingPlace.getStartTime())
                        .andStartTimeLessThanOrEqualTo(tbMeetingPlace.getEndTime())
                        .andStatusNotIn(Lists.newArrayList(1, 4));

                List<TbMeetingPlace> meetingPlaceList = meetingPlaceExtMapper.selectByExample(example);
                if (!CollectionUtils.isEmpty(meetingPlaceList)) {
                    throw new CosmeticException(ErrorCodeEnum.PARAM_ERROR.getErrCode(), "会场时间冲突");
                }
            }

            TbMeetingPlace meetingPlace = BeanUtil.createCloneBean(meetingPlaceForm, TbMeetingPlace.class);
            tbMeetingPlace.setGmtModified(new Date());
            int count = meetingPlaceExtMapper.updateByPrimaryKeySelective(meetingPlace);
            if ( count == 0) {
                throw new CosmeticException(ErrorCodeEnum.DATA_ERROR);
            }
        } catch (CosmeticException e) {
            throw e;
        } catch (Exception e) {
            throw new CosmeticException(ErrorCodeEnum.EXCEPTION);
        }

    }

    /**
     * 根据条件查询会场信息
     *
     * @param meetingPlaceForm
     * @return
     * @throws CosmeticException
     */
    @Override
    public BizResult<List<MeetingPlaceVO>> selectMeetingPlaceByConditions(MeetingPlaceForm meetingPlaceForm) throws CosmeticException {
        try {
            BizResult<List<MeetingPlaceVO>> bizResult = new BizResult<>();

            //查询会场更新会场状态
            List<MeetingPlaceVO> meetingPlaceVOS = meetingPlaceExtMapper.selectMeetingPlaceByConditions(meetingPlaceForm);
            if ( CollectionUtils.isEmpty(meetingPlaceVOS )) {
                return BizResult.create(Lists.newArrayList());
            }


            for (MeetingPlaceVO meetingPlaceVO : meetingPlaceVOS) {
                Integer status = meetingPlaceVO.getStatus();
                long nowTime = System.currentTimeMillis();
                if (nowTime < meetingPlaceVO.getStartTime() &&
                        (meetingPlaceVO.getStatus().compareTo(MeetingPlaceStatusEnum.UNPUBLISHED.getCode()) != 0) ) {
                    //判断此事时间还未到会场开始时间且会场不处于未发布状态，则设置会场状态为未开始
                    status = MeetingPlaceStatusEnum.NO_START.getCode();
                } else if ( nowTime >= meetingPlaceVO.getStartTime() && nowTime <= meetingPlaceVO.getEndTime() &&
                        (meetingPlaceVO.getStatus().compareTo(MeetingPlaceStatusEnum.UNPUBLISHED.getCode()) != 0)) {
                    //判断此时时间为会场开始时间与结束时间之间且会场不处于未发布状态，则设置会场状态为进行中
                    status = MeetingPlaceStatusEnum.PROCESSING.getCode();
                } else if (nowTime > meetingPlaceVO.getEndTime() &&
                        (meetingPlaceVO.getStatus().compareTo(MeetingPlaceStatusEnum.UNPUBLISHED.getCode()) != 0) ) {
                    //判断此时时间已过会场结束时间且会场不处于未发布状态，则设置会场状态为已结束
                    status = MeetingPlaceStatusEnum.OVER.getCode();
                }
                meetingPlaceVO.setStatus(status);
                //将此时的会场状态写会数据库
                MeetingPlaceForm meetingPlaceForm1 = new MeetingPlaceForm();
                meetingPlaceForm1.setId(meetingPlaceVO.getId());
                meetingPlaceForm1.setStatus(status);
                this.updateMeetingPlaceById(meetingPlaceForm1);
            }

            //分页处理
            PageHelper.startPage(meetingPlaceForm.getPageNo(), meetingPlaceForm.getPageSize());
            List<MeetingPlaceVO> meetingPlaceVOList = meetingPlaceExtMapper.selectMeetingPlaceByConditions(meetingPlaceForm);

            PageInfo<MeetingPlaceVO> pageInfo = new PageInfo<>(meetingPlaceVOS);
            long total = pageInfo.getTotal();

            bizResult.setTotalCount((int)total);
            bizResult.setPageSize(meetingPlaceForm.getPageSize());
            bizResult.setPageNo(meetingPlaceForm.getPageNo());
            bizResult.setData(meetingPlaceVOList);
            bizResult.setSuccess(true);
            return bizResult;
        } catch (CosmeticException e) {
            throw e;
        } catch (Exception e) {
            throw new CosmeticException(ErrorCodeEnum.EXCEPTION);
        }
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
    }
}
