package com.cos.dao.pojo;

import java.util.Date;

public class TbOrder {
    private Long id;

    private String orderId;

    private Long payment;

    private Integer status;

    private String userId;

    private Integer buyyerRate;

    private Date gmtCreat;

    private Date gmtModified;

    private String receiveName;

    private String receivePhone;

    private String receiveAddress;

    private String buyyerRemark;

    private Long meetingPlaceId;

    private String nickName;

    private String userPic;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Long getPayment() {
        return payment;
    }

    public void setPayment(Long payment) {
        this.payment = payment;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Integer getBuyyerRate() {
        return buyyerRate;
    }

    public void setBuyyerRate(Integer buyyerRate) {
        this.buyyerRate = buyyerRate;
    }

    public Date getGmtCreat() {
        return gmtCreat;
    }

    public void setGmtCreat(Date gmtCreat) {
        this.gmtCreat = gmtCreat;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName == null ? null : receiveName.trim();
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone == null ? null : receivePhone.trim();
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress == null ? null : receiveAddress.trim();
    }

    public String getBuyyerRemark() {
        return buyyerRemark;
    }

    public void setBuyyerRemark(String buyyerRemark) {
        this.buyyerRemark = buyyerRemark == null ? null : buyyerRemark.trim();
    }

    public Long getMeetingPlaceId() {
        return meetingPlaceId;
    }

    public void setMeetingPlaceId(Long meetingPlaceId) {
        this.meetingPlaceId = meetingPlaceId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic == null ? null : userPic.trim();
    }
}