package com.cos.common.enums;

/**
 * @description: 异常枚举类
 * @author: hzf
 * @date: 2018/1/20 下午3:38
 */
public enum ErrorCodeEnum {

    SUCCESS("0000", "成功"),
    NO_DATA("1002", "无数据"),
    PARAM_ERROR("1003", "参数错误"),
    INSERT_FAIL("1004", "插入数据错误"),
    QUERY_FAIL("1005", "查询错误"),
    UPDATE_FAIL("1006", "修改数据错误"),
    DATA_ERROR("1008", "数据异常"),
    DUPLICATE_ERROR("1009", "重复操作"),
    UPLOAD_ERROR("1010", "文件上传失败"),
    NO_AUTH("1011", "无操作权限"),
    NO_BRAND("1012", "该品牌不存在"),
    EXCEPTION("9999", "系统异常");



    private String errCode;

    private String errMsg;

    ErrorCodeEnum(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
