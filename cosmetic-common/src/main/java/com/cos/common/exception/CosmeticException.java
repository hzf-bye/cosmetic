package com.cos.common.exception;

import com.cos.common.enums.ErrorCodeEnum;
import org.apache.commons.lang3.StringUtils;

/**
 * @description: cosmetic异常类
 * @author: hzf
 * @date: 2018/1/20 下午3:45
 */
public class CosmeticException extends RuntimeException{

    private static final long serialVersionUID = 3452345246238989L;

    /**
     * 异常code
     */
    private String code;

    /**
     * 调用接口的请求参数
     */
    private Object request;

    /**
     * response, 调用接口的返回结果
     */
    private Object response;

    /**
     * 错误的信息
     */
    private String message;

    public CosmeticException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public CosmeticException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 根据异常码,cause构建Exception
     *
     * @param errorCodeEnum
     * @param cause
     */
    public CosmeticException(ErrorCodeEnum errorCodeEnum, Throwable cause) {
        super(errorCodeEnum.getErrMsg(), cause);
        this.code = errorCodeEnum.getErrCode();
        this.message = errorCodeEnum.getErrMsg();
    }

    /**
     * 根据异常码构建Exception
     *
     * @param errorCodeEnum
     */
    public CosmeticException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getErrMsg());
        this.code = errorCodeEnum.getErrCode();
        this.message = errorCodeEnum.getErrMsg();
    }

    /**
     * 自定义Exception
     *
     * @param errorCodeEnum
     * @param code
     * @param message
     */
    public CosmeticException(ErrorCodeEnum errorCodeEnum, String code, String message) {
        this.code = errorCodeEnum.getErrCode();
        this.message = errorCodeEnum.getErrMsg();
        if (StringUtils.isNotBlank(code)) {
            this.code = code;
        }
        if (StringUtils.isNotBlank(message)) {
            this.message = message;
        }
    }

    public CosmeticException(Throwable cause) {
        super(cause);
    }

    public CosmeticException setCode(String code) {
        this.code = code;
        return this;
    }

    public CosmeticException setRequest(Object request) {
        this.request = request;
        return this;
    }

    public CosmeticException setResponse(Object response) {
        this.response = response;
        return this;
    }

    public CosmeticException setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public Object getRequest() {
        return this.request;
    }

    public Object getResponse() {
        return this.response;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
