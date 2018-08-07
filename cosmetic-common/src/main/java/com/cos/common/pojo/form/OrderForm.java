package com.cos.common.pojo.form;

import com.cos.common.pojo.vo.OrderDetailVO;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @description: 订单form
 * @author: hzf
 * @date: 2018/2/7 下午3:54
 */
@Data
public class OrderForm extends BaseForm{

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 实付金额
     */
    @NotNull(message = "实付金额不能为空")
    private Long payment;

    /**
     * 订单状态
     */
    @NotNull(message = "订单状态不能为空")
    private Integer status;

    private List<Integer> orderStatus;

    /**
     * 买家id
     */
    @NotEmpty(message = "买家id不能为空")
    private String userId;

    /**
     * 用户头像
     */
    @NotNull(message = "用户头像不能为空")
    private String userPic;

    /**
     * 买家昵称
     */
    @NotBlank(message = "买家昵称不能为空")
    private String nickName;

    /**
     * 收货人姓名
     */
    @NotBlank(message = "收货人姓名不能为空")
    private String receiveName;

    /**
     * 收货人电话
     */
    @NotBlank(message = "收货人电话不能为空")
    private String receivePhone;

    /**
     * 收货人地址
     */
    @NotBlank(message = "收货人地址不能为空")
    private String receiveAddress;

    /**
     * 买家留言
     */
    private String buyyerRemark;

    /**
     * 下单时间
     */
    private Date gmtCreat;

    /**
     * 会场id
     */
    @NotNull(message = "会场id不能为空")
    private Long meetingPlaceId;

    /**
     * 会场名
     */
    private String meetingPlaceName;

    /**
     * 订单明细
     */
    @NotEmpty(message = "订单明细不能为空")
    @Valid
    private List<OrderDetailForm> orderDetailForms;

    /**
     * 开始时间String格式
     */
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String startTime;

    /**
     * 查询订单时开始时间
     */
    private Date startDateTime;

    /**
     * 结束时间String格式
     */
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String endTime;

    /**
     * 查询订单时结束时间
     */
    private Date endDateTime;
}
