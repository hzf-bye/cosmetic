package com.cos.service.intf;

import com.cos.common.entity.BizResult;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.form.UserForm;
import com.cos.common.pojo.vo.UserVO;
import com.cos.pojo.request.UserRequest;
import com.cos.pojo.response.UserResponse;

import java.util.List;

/**
 * @description: 用户表service
 * @author: hzf
 * @date: 2018/1/20 下午2:49
 */
public interface UserService {

    /**
     * 新增用户
     * @param userForm
     * @throws CosmeticException
     */
    void insertUser(UserForm userForm) throws CosmeticException;

    /**
     * 更新用户信息
     * @param userForm
     * @throws CosmeticException
     */
    void updateUser(UserForm userForm) throws CosmeticException;

    /**
     * 删除用户信息
     * @param username
     * @throws CosmeticException
     */
    void deleteUser(String username) throws CosmeticException;

    /**
     * 校验注册时的用户名是否重复
     */
    BizResult<Boolean> checkData(String username) throws CosmeticException;

    /**
     * 登录
     * @param userForm
     * @return
     * @throws CosmeticException
     */
    BizResult<Integer> login(UserForm userForm) throws CosmeticException;

    /**
     * 查询所有用户信息
     */
    BizResult<List<UserVO>> getUsersInfo(UserForm userForm) throws CosmeticException;
}
