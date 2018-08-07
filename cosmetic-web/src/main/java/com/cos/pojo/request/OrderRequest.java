package com.cos.pojo.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description: 购物车请求参数
 * @author: hzf
 * @date: 2018/4/8 上午10:01
 */
@Data
public class OrderRequest extends BaseRequest{

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 会场id
     */
    private Long meetingPlaceId;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 收件人姓名
     */
    private String receiveName;

    /**
     * 收货人手机号
     */
    private String phone;

    /**
     * 收货人地址
     */
    private  String address;
}
