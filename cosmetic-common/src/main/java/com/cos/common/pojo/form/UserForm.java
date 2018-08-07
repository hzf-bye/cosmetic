package com.cos.common.pojo.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @Description: 用户form
 * @author: hzf
 * @Date: 2018/5/8 下午4:27
 */
@Data
public class UserForm extends BaseForm {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 是否是超级管理员 1：是 0：否
     */
    private Integer isAdmin;
}
