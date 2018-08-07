package com.cos.pojo.response;

import lombok.Data;

import java.util.Date;


/**
 * @description:
 * @author: hzf
 * @date: 2018/1/20 下午4:18
 */
@Data
public class UserResponse {

    private Long id;

    private String username;

    private String password;

    private String phone;

    private String email;


}
