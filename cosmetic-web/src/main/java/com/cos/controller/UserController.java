package com.cos.controller;

import com.alibaba.fastjson.JSON;
import com.cos.common.entity.BizResult;
import com.cos.common.enums.ErrorCodeEnum;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.form.UserForm;
import com.cos.common.pojo.vo.UserVO;
import com.cos.common.utils.JsonFormValidatorUtil;
import com.cos.pojo.request.UserRequest;
import com.cos.pojo.response.UserResponse;
import com.cos.service.intf.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * @description: 用户表controller
 * @author: hzf
 * @date: 2018/1/20 下午2:55
 */
@Controller
@RequestMapping("user")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 新增用户
     * @param userForm
     * @param errors
     * @return
     */
    @ResponseBody
    @RequestMapping("register")
    public BizResult<Boolean> insertUser(@RequestBody @Valid UserForm userForm, Errors errors){

        try {
            JsonFormValidatorUtil.validate(errors);
            userService.insertUser(userForm);
            return BizResult.create(true);
        } catch (CosmeticException e) {
            log.error("新增错误,userForm is {}", JSON.toJSONString(userForm), e);
            return BizResult.create(e.getCode(), e.getMessage());
        }

    }

    /**
     * 修改用户信息
     * @param userForm
     * @return
     */
    @ResponseBody
    @RequestMapping("update")
    public BizResult<Boolean> updateUser(@RequestBody @Valid UserForm userForm, Errors errors){

        try {
            JsonFormValidatorUtil.validate(errors);
            userService.updateUser(userForm);
            return BizResult.create(true);
        } catch (CosmeticException e) {
            log.error("修改信息错误,userForm is {}", JSON.toJSONString(userForm), e);
            return BizResult.create(e.getCode(), e.getMessage());
        }

    }

    /**
     * 删除用户信息
     * @param userForm
     * @return
     */
    @ResponseBody
    @RequestMapping("delete")
    public BizResult<Boolean> deleteUser(@RequestBody UserForm userForm){

        try {
            userService.deleteUser(userForm.getUsername());
            return BizResult.create(true);
        } catch (CosmeticException e) {
            log.error("修改信息错误,userForm is {}", JSON.toJSONString(userForm), e);
            return BizResult.create(e.getCode(), e.getMessage());
        }

    }

    /**
     * 校验注册时的用户名是否重复
     * @param userForm
     * @return
     */
    @ResponseBody
    @RequestMapping("check")
    public BizResult<Boolean> checkData(@RequestBody UserForm userForm){

        try {
            return userService.checkData(userForm.getUsername());
        } catch (CosmeticException e) {
            log.error("校验注册时的用户名是否重复,userForm is {}", JSON.toJSONString(userForm), e);
            return BizResult.create(e.getCode(), e.getMessage());
        }

    }

    /**
     * 登录
     * @param userForm
     * @return
     */
    @ResponseBody
    @RequestMapping("login")
    public BizResult<Integer> login(@RequestBody @Valid UserForm userForm, Errors errors){

        try {
            JsonFormValidatorUtil.validate(errors);
            return userService.login(userForm);
        } catch (CosmeticException e) {
            log.error("登录错误,userForm is {}", JSON.toJSONString(userForm), e);
            return BizResult.create(e.getCode(), e.getMessage());
        }

    }

    /**
     * 查询所有用户信息
     */
    @ResponseBody
    @RequestMapping("getUsersInfo")
    public BizResult<List<UserVO>> getUsersInfo(@RequestBody UserForm userForm){

        try {
            return userService.getUsersInfo(userForm);
        } catch (CosmeticException e) {
            log.error("查询错误 ",e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }

}
