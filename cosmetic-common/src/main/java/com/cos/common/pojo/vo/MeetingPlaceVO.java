package com.cos.common.pojo.vo;

import lombok.Data;

/**
 * @description: 会场VO
 * @author: hzf
 * @date: 2018/2/7 上午11:09
 */
@Data
public class MeetingPlaceVO {

    /**
     * 会场id
     */
    private Long id;

    /**
     * 品牌名
     */
    private String brandName;

    /**
     * 品牌id
     */
    private Long brandId;

    /**
     * 会场名
     */
    private String name;

    /**
     * 会差开始时间，时间的毫秒数
     */
    private Long startTime;

    /**
     * 会差结束时间，时间的毫秒数
     */
    private Long endTime;

    /**
     * 会场状态
     */
    private Integer status;


}
