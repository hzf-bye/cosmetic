package com.cos.common.utils;

import com.cos.common.enums.ErrorCodeEnum;
import com.cos.common.exception.CosmeticException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

/**
 * @description: 错误信息解析类
 * @author: hzf
 * @date: 2018/3/22 下午4:39
 */
public class JsonFormValidatorUtil {

    public static void validate(Errors errors) throws CosmeticException {
        if (errors.hasErrors()) {
            if (!CollectionUtils.isEmpty(errors.getAllErrors())) {
                StringBuilder sbd = new StringBuilder();
                for (ObjectError error : errors.getAllErrors()) {
                    sbd.append(error.getDefaultMessage() + ";");
                }
                throw new CosmeticException(ErrorCodeEnum.PARAM_ERROR.getErrCode(), sbd.toString());
            } else {
                throw new CosmeticException(ErrorCodeEnum.PARAM_ERROR);
            }
        }
    }

}

