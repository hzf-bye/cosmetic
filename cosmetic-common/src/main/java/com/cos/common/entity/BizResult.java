package com.cos.common.entity;

import com.cos.common.enums.ErrorCodeEnum;
import com.cos.common.query.BaseQuery;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Map;

/**
 * @description: 返回数据给前台的工具类
 * @author: hzf
 * @date: 2018/1/20 下午3:29
 */
public class BizResult<T> implements Serializable {
    private boolean success = false;
    private String code;
    private String message;
    private T data;
    private Map<String, Object> extraInfo;
    private int pageNo;
    private int totalCount;
    private int pageSize;

    public BizResult() {
        this.pageNo = BaseQuery.DEFAULT_PAGE;
        this.pageSize = BaseQuery.DEFAULT_PAGE_SIZE;
    }

    public static <T> BizResult<T> create() {
        return new BizResult();
    }

    public static <T> BizResult<T> create(T data) {
        BizResult<T> bizResult = create();
        bizResult.setSuccess(true);
        bizResult.setData(data);
        return bizResult;
    }

    public static <T> BizResult<T> create(T data, String code, String message) {
        BizResult<T> result = create();
        result.setSuccess(true);
        result.setData(data);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> BizResult<T> create(T data, boolean isSuccess, String code, String message) {
        BizResult<T> result = create();
        result.setSuccess(isSuccess);
        result.setData(data);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> BizResult<T> create(T data, Map<String, Object> extraInfo) {
        BizResult<T> bizResult = create();
        bizResult.setSuccess(true);
        bizResult.setData(data);
        bizResult.setExtraInfo(extraInfo);
        return bizResult;
    }

    public static <T> BizResult<T> create(T data, Map<String, Object> extraInfo, boolean isSuccess, String code, String message) {
        BizResult<T> bizResult = create();
        bizResult.setSuccess(true);
        bizResult.setData(data);
        bizResult.setExtraInfo(extraInfo);
        bizResult.setSuccess(isSuccess);
        bizResult.setCode(code);
        bizResult.setMessage(message);
        return bizResult;
    }

    public static <T> BizResult<T> create(String code, String message) {
        BizResult<T> bizResult = create();
        bizResult.setSuccess(false);
        bizResult.setCode(code);
        bizResult.setMessage(message);
        return bizResult;
    }

    public static <T> BizResult<T> create(ErrorCodeEnum errorCodeEnum) {
        BizResult<T> bizResult = create();
        bizResult.setSuccess(false);
        bizResult.setCode(errorCodeEnum.getErrCode());
        bizResult.setMessage(errorCodeEnum.getErrMsg());
        return bizResult;
    }

    public Map<String, Object> getExtraInfo() {
        return this.extraInfo;
    }

    public void setExtraInfo(Map<String, Object> extraInfo) {
        this.extraInfo = extraInfo;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    @JsonIgnore
    public int getTotalPage() {
        if (this.pageNo < 1) {
            this.pageNo = BaseQuery.DEFAULT_PAGE;
        }

        return this.totalCount / this.pageSize + (this.totalCount % this.pageSize == 0 ? 0 : 1);
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}

