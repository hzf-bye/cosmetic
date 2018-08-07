package com.cos.service.impl;

import com.cos.common.entity.BizResult;
import com.cos.common.enums.CommonStatusEnum;
import com.cos.common.enums.ErrorCodeEnum;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.form.UserForm;
import com.cos.common.pojo.vo.UserVO;
import com.cos.common.utils.BeanUtil;
import com.cos.dao.mapper.TbUserMapper;
import com.cos.dao.pojo.TbUser;
import com.cos.dao.pojo.TbUserExample;
import com.cos.pojo.request.UserRequest;
import com.cos.pojo.response.UserResponse;
import com.cos.service.intf.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 用户表service实现类
 * @author: hzf
 * @date: 2018/1/20 下午2:51
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    TbUserMapper tbUserMapper;

    /**
     * 新增用户
     *
     * @param userForm
     * @throws CosmeticException
     */
    @Override
    public void insertUser(UserForm userForm) throws CosmeticException {

        try {
            TbUser tbUser = BeanUtil.createCloneBean(userForm, TbUser.class);
            tbUser.setGmtCreat(new Date());
            tbUser.setGmtModifed(new Date());
            tbUserMapper.insertSelective(tbUser);
        } catch (Exception e) {
            throw new CosmeticException(ErrorCodeEnum.EXCEPTION);
        }
    }

    /**
     * 更新用户信息
     *
     * @param userForm
     * @throws CosmeticException
     */
    @Override
    public void updateUser(UserForm userForm) throws CosmeticException {

        TbUser tbUser = BeanUtil.createCloneBean(userForm, TbUser.class);
        tbUser.setGmtModifed(new Date());
        TbUserExample userExample = new TbUserExample();
        userExample.createCriteria().andUsernameEqualTo(userForm.getUsername())
                .andIsDeletedEqualTo(CommonStatusEnum.NO.getCode());
        try {
            tbUserMapper.updateByExampleSelective(tbUser, userExample);
        } catch (Exception e) {
            throw new CosmeticException(ErrorCodeEnum.EXCEPTION);
        }

    }

    /**
     * 删除用户信息
     *
     * @param username
     * @throws CosmeticException
     */
    @Override
    public void deleteUser(String username) throws CosmeticException {

        TbUserExample userExample = new TbUserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        try {
            tbUserMapper.deleteByExample(userExample);
        } catch (Exception e) {
            throw new CosmeticException(ErrorCodeEnum.EXCEPTION);
        }

    }

    /**
     * 校验注册时的用户名是否重复
     *
     * @param username
     */
    @Override
    public BizResult<Boolean> checkData(String username) throws CosmeticException {

        TbUserExample userExample = new TbUserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        try {
            List<TbUser> tbUsers =  tbUserMapper.selectByExample(userExample);
            if (CollectionUtils.isEmpty(tbUsers)) {
               return BizResult.create(true);
            } else {
                return BizResult.create("9999", "用户名重复");
            }
        } catch (Exception e) {
            throw new CosmeticException(ErrorCodeEnum.EXCEPTION);
        }
    }

    /**
     * 登录
     *
     * @param userForm
     * @return
     * @throws CosmeticException
     */
    @Override
    public BizResult<Integer> login(UserForm userForm) throws CosmeticException {

        TbUserExample userExample = new TbUserExample();
        userExample.createCriteria().andUsernameEqualTo(userForm.getUsername())
                .andPasswordEqualTo(userForm.getPassword()).andIsDeletedEqualTo(CommonStatusEnum.NO.getCode());

        try {
            List<TbUser> tbUsers =  tbUserMapper.selectByExample(userExample);
            if (!CollectionUtils.isEmpty(tbUsers)) {
                return BizResult.create(tbUsers.get(0).getIsAdmin());
            } else {
                return BizResult.create("9999", "用户名或密码错误");
            }
        } catch (Exception e) {
            throw new CosmeticException(ErrorCodeEnum.EXCEPTION);
        }

    }

    /**
     * 查询所有用户信息
     */
    @Override
    public BizResult<List<UserVO>> getUsersInfo(UserForm userForm) throws CosmeticException {

        TbUserExample userExample = new TbUserExample();
        TbUserExample.Criteria criteria = userExample.createCriteria();
        criteria.andIsDeletedEqualTo(CommonStatusEnum.NO.getCode());
        if (userForm != null && !StringUtils.isEmpty(userForm.getUsername())) {
            criteria.andUsernameLike("%" + userForm.getUsername() + "%");
        }

        try {
            List<TbUser> tbUsers =  tbUserMapper.selectByExample(userExample);
            if (CollectionUtils.isEmpty(tbUsers)) {
                return BizResult.create(Lists.newArrayList());
            }

            List<UserVO> userVOS = tbUsers.stream().map(tbUser ->
                    BeanUtil.createCloneBean(tbUser, UserVO.class)).collect(Collectors.toList());
            return BizResult.create(userVOS);
        } catch (Exception e) {
            throw new CosmeticException(ErrorCodeEnum.QUERY_FAIL);
        }

    }


}
