package com.cos.common.pojo.vo;

import com.cos.common.pojo.form.BaseForm;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @Description: 用户vo888os
 * @author: hzf
 * @Date: 2018/5/8 下午4:27
 */
@Data
public class UserVO extends BaseForm {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 是否是超级管理员 1：是 0：否
     */
    private Integer isAdmin;
}
