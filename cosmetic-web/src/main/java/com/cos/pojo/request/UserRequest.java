package com.cos.pojo.request;

import com.cos.common.query.BaseQuery;
import lombok.Data;

/**
 * @description: 用户数据入参
 * @author: hzf
 * @date: 2018/1/20 下午4:06
 */
@Data
public class UserRequest extends BaseQuery {

    private Long id;

    private String username;

    private String password;

    private String phone;

    private String email;
}
