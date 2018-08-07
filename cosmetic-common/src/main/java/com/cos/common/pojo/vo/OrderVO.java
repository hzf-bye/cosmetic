package com.cos.common.pojo.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @description: 订单vo
 * @author: hzf
 * @date: 2018/2/7 下午3:14
 */
@Data
public class OrderVO {

    /**
     * 订单表主键
     */
    private Long id;

    /**
     * 订单id
     */
    private String orderId;

    /**
     *  实付金额，单位分
     */
    private Long payment;

    /**
     * 实付金额，单位元
     */
    private String paymentYuan;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 买家id
     */
    private String userId;

    /**
     * 买家头像
     */
    private String userPic;

    /**
     * 买家姓名
     */
    private String nickName;

    /**
     * 收货人姓名
     */
    private String receiveName;

    /**
     * 收货人电话
     */
    private String receivePhone;

    /**
     * 收货人地址
     */
    private String receiveAddress;

    /**
     * 买家留言
     */
    private String remark;

    /**
     * 下单时间
     */
    private Date orderTime;

    /**
     * 订单修改时间
     */
    private Date orderModifiedTime;

    /**
     * 订单明细
     */
    private List<OrderDetailVO> orderDetailVOS;

    /**
     * 会场id
     */
    private Long meetingPlaceId;

    /**
     * 会场名
     */
    private String meetingPlaceName;

    /**
     * 订单对应商品名称
     */
    private List<String> goodsNames;
}
