package com.cos.order.controller;

import com.alibaba.fastjson.JSON;
import com.cos.common.entity.BizResult;
import com.cos.common.enums.ErrorCodeEnum;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.form.OrderForm;
import com.cos.common.pojo.vo.OrderVO;
import com.cos.common.utils.JsonFormValidatorUtil;
import com.cos.order.service.intf.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @Description: 订单controller
 * @author: hzf
 * @Date: 2018/4/14 下午3:09
 */
@RestController
@Slf4j
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("createOrder")
    public BizResult<String> createOrder(@RequestBody @Valid OrderForm orderForm, Errors errors){
        try {
            JsonFormValidatorUtil.validate(errors);
            return orderService.createOrder(orderForm);
        } catch (CosmeticException e) {
            log.error("创建订单失败 orderForm is {}", JSON.toJSONString(orderForm), e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }

    /**
     * 根据订单id更改订单状态
     * @param orderForm
     * @return
     */
    @ResponseBody
    @RequestMapping("updateOrderStatusByOrderId")
    public BizResult updateOrderStatusByOrderId(@RequestBody OrderForm orderForm){

        if (StringUtils.isBlank(orderForm.getOrderId())) {
            log.error("根据订单id更新订单状态失败, orderForm is {}", JSON.toJSONString(orderForm));
            return BizResult.create(ErrorCodeEnum.PARAM_ERROR);
        }

        try {
            return orderService.updateOrderStatusByOrderId(orderForm);
        } catch (CosmeticException e) {
            log.error("根据订单id更新订单状态失败, orderForm is {}", JSON.toJSONString(orderForm), e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }

    /**
     * 根据查询条件组合查询订单以及订单明细
     * @param orderForm
     * @return
     */
    @ResponseBody
    @RequestMapping("selectOrdersByConditions")
    BizResult<List<OrderVO>> selectOrdersByConditions(@RequestBody OrderForm orderForm) {

        try {
            return orderService.selectOrdersByConditions(orderForm);
        } catch (CosmeticException e) {
            log.error("根根据查询条件组合查询订单以及订单明细失败, orderForm is {}", JSON.toJSONString(orderForm));
            return BizResult.create(e.getCode(), e.getMessage());
        }

    }

    /**
     * 根据订单id查询订单明细
     * @param orderForm
     * @return
     */
    @ResponseBody
    @RequestMapping("selectOrderDetailByOrderId")
    public BizResult selectOrderDetailByOrderId(@RequestBody OrderForm orderForm){
        try {
            BizResult bizResult = orderService.selectOrderDetailByOrderId(orderForm.getOrderId(),
                    orderForm.getPageNo(), orderForm.getPageSize());
            return bizResult;
        } catch (CosmeticException e) {
            log.error("根据订单id查询订单明细失败, orderId is {}", orderForm.getOrderId(), e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }
}
