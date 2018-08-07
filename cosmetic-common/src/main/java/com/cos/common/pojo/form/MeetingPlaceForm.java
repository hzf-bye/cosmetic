package com.cos.common.pojo.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @description: 会场form
 * @author: hzf
 * @date: 2018/2/7 上午11:35
 */
@Data
public class MeetingPlaceForm extends BaseForm{

    /**
     * 会场id
     */
    private Long id;

    /**
     * 品牌名
     */
    private Long brandId;

    /**
     * 品牌名
     */
    private String brandName;

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
