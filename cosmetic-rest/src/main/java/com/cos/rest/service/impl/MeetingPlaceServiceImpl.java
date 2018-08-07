package com.cos.rest.service.impl;

import com.cos.common.entity.BizResult;
import com.cos.common.enums.ErrorCodeEnum;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.vo.MeetingPlaceVO;
import com.cos.common.utils.BeanUtil;
import com.cos.dao.mapper.TbBrandMapper;
import com.cos.dao.mapper.ext.TbBrandExtMapper;
import com.cos.dao.mapper.ext.TbGoodsExtMapper;
import com.cos.dao.mapper.ext.TbMeetingPlaceExtMapper;
import com.cos.dao.pojo.*;
import com.cos.rest.service.intf.MeetingPlaceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.List;
import java.util.stream.Collectors;

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
    private TbGoodsExtMapper goodsMapper;

    /**
     * 查询当前时刻进行中的会场
     *
     * @param pageNo
     * @param pageSize
     * @return
     * @throws CosmeticException
     */
    @Override
    public BizResult<List<MeetingPlaceVO>> selectMeetingPlace(Integer pageNo, Integer pageSize) throws CosmeticException {
        try {
            BizResult<List<MeetingPlaceVO>> bizResult = new BizResult<>();
            //分页处理
            PageHelper.startPage(pageNo, pageSize);
            long nowTime = System.currentTimeMillis();
            TbMeetingPlaceExample meetingPlaceExample = new TbMeetingPlaceExample();
            meetingPlaceExample.createCriteria().andEndTimeGreaterThan(nowTime)
                    .andStartTimeLessThanOrEqualTo(nowTime)
                    .andStatusNotEqualTo(1);
            meetingPlaceExample.setOrderByClause("start_time asc");
            List<TbMeetingPlace> meetingPlaces = meetingPlaceExtMapper.selectByExample(meetingPlaceExample);
            if ( CollectionUtils.isEmpty(meetingPlaces) ) {
                throw new CosmeticException(ErrorCodeEnum.NO_DATA);
            }

            PageInfo<TbMeetingPlace> pageInfo = new PageInfo<>(meetingPlaces);
            long total = pageInfo.getTotal();

            List<MeetingPlaceVO> meetingPlaceVOS = meetingPlaces.stream().map(meetingPlace -> {

                MeetingPlaceVO meetingPlaceVO = BeanUtil.createCloneBean(meetingPlace, MeetingPlaceVO.class);
                TbBrand tbBrand = brandMapper.selectByPrimaryKey(meetingPlace.getBrandId());
                meetingPlaceVO.setBrandName(tbBrand.getBrandName());
                return  meetingPlaceVO;
            }).collect(Collectors.toList());

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

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
    }
}
