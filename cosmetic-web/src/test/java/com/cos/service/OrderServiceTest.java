package com.cos.service;

import com.cos.base.BaseTest;
import com.cos.common.entity.BizResult;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.form.OrderForm;
import com.cos.service.intf.OrderService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description: 订单service测试类
 * @author: hzf
 * @date: 2018/2/7 下午7:06
 */
public class OrderServiceTest extends BaseTest {

    @Autowired
    private OrderService orderService;


    @Test
    public void selectOrdersByConditionsTest(){
        try {
            OrderForm orderForm = new OrderForm();
            orderForm.setOrderId("296543");
            orderForm.setReceivePhone("7654");
            orderForm.setStatus(1);
            orderForm.setPageNo(1);
            orderForm.setPageSize(10);
            BizResult bizResult = orderService.selectOrdersByConditions(orderForm);
            System.out.println();
        } catch (CosmeticException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void selectOrderDetailByOrderIdTest(){

        try {
            BizResult bizResult = orderService.selectOrderDetailByOrderId("296543", 1, 10);
            System.out.println();
        } catch (CosmeticException e) {
            e.printStackTrace();
        }

    }
}
