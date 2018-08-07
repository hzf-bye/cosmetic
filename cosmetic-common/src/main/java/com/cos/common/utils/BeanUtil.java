package com.cos.common.utils;

import org.springframework.beans.BeanUtils;

/**
 * @description:
 * @author: hzf
 * @date: 2018/1/20 下午4:11
 */
public class BeanUtil {

    public static <T> T createCloneBean(Object source, Class<T> targetClaz) {
        try {
            T destObj = targetClaz.newInstance();
            BeanUtils.copyProperties(source, destObj);
            return destObj;
        } catch (Exception e) {
            throw new RuntimeException("Copy prop error.", e);
        }
    }

}
