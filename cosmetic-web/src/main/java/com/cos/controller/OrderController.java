package com.cos.controller;

import com.alibaba.fastjson.JSON;
import com.cos.common.entity.BizResult;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.form.OrderForm;
import com.cos.pojo.request.OrderRequest;
import com.cos.service.intf.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @description: 订单controller
 * @author: hzf
 * @date: 2018/2/7 下午9:33
 */
@Slf4j
@Controller
@RequestMapping("order")
public class OrderController {

    @Autowired
    OrderService orderService;

    /**
     * 根据查询条件组合查询订单
     * @param orderForm
     * @return
     */
    @ResponseBody
    @RequestMapping("selectOrdersByConditions")
    public BizResult selectOrdersByConditions(@RequestBody OrderForm orderForm) {

        try {
            BizResult bizResult = orderService.selectOrdersByConditions(orderForm);
            return bizResult;
        } catch (CosmeticException e) {
            log.error("根据查询条件组合查询订单失败, orderForm is {}", JSON.toJSONString(orderForm), e);
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

    /**
     * 根据订单id更改订单状态
     * @param orderForm
     * @return
     */
    @ResponseBody
    @RequestMapping("updateOrderStatusByOrderId")
    public BizResult updateOrderStatusByOrderId(@RequestBody OrderForm orderForm){

        try {
            BizResult bizResult = orderService.updateOrderStatusByOrderId(orderForm);
            return bizResult;
        } catch (CosmeticException e) {
            log.error("根据订单id更新订单状态失败, orderId is {}", orderForm.getOrderId(), e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }
}
