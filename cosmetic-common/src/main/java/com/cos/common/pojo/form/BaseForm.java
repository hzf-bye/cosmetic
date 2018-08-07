package com.cos.common.pojo.form;

import lombok.Data;

/**
 * @description:
 * @author: hzf
 * @date: 2018/4/1 下午7:37
 */
@Data
public class BaseForm {

    /**
     * 页大小
     */
    private Integer pageSize = 10;

    /**
     * 页号
     */
    private Integer pageNo = 1;
}
