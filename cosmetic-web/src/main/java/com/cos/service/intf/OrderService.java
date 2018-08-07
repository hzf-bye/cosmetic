package com.cos.service.intf;

import com.cos.common.entity.BizResult;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.form.OrderForm;
import com.cos.common.pojo.vo.OrderDetailVO;
import com.cos.common.pojo.vo.OrderVO;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

/**
 * @description: 订单servi ce
 * @author: hzf
 * @date: 2018/2/7 下午4:33
 */
public interface OrderService {


    /**
     * 根据查询条件组合查询订单
     * @param orderForm
     * @return
     * @throws CosmeticException
     */
    BizResult<List<OrderVO>> selectOrdersByConditions(OrderForm orderForm) throws CosmeticException;

    /**
     * 根据订单id更改订单状态
     * @param orderForm
     * @return
     * @throws CosmeticException
     */
    BizResult<Boolean> updateOrderStatusByOrderId(OrderForm orderForm) throws CosmeticException;

    /**
     * 根据订单id查询订单明细
     * @param orderId
     * @param pageNo
     * @param pageSize
     * @return
     * @throws CosmeticException
     */
    BizResult<OrderVO> selectOrderDetailByOrderId(String orderId, Integer pageNo, Integer pageSize) throws CosmeticException;

}
