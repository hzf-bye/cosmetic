package com.cos.pojo.request;

import lombok.Data;

/**
 * @description:
 * @author: hzf
 * @date: 2018/4/1 下午8:15
 */
@Data
public class BaseRequest {

    /**
     * 页大小
     */
    private Integer pageSize = 1;

    /**
     * 页号
     */
    private Integer pageNo = 10;

}
