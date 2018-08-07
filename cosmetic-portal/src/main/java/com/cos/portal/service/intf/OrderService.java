package com.cos.portal.service.intf;

import com.cos.common.entity.BizResult;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.form.OrderForm;
import com.cos.common.pojo.vo.OrderVO;

import java.util.List;

/**
 * @description: 订单service
 * @author: hzf
 * @date: 2018/4/11 下午7:29
 */
public interface OrderService {

    /**
     * 创建订单及订单详情
     * @param orderForm
     * @return
     * @throws CosmeticException
     */
    BizResult<String> createOrder(OrderForm orderForm) throws CosmeticException;

    /**
     * 根据订单id更改订单状态
     * @param orderForm
     * @return
     * @throws CosmeticException
     */
    BizResult<Boolean> updateOrderStatusByOrderId(OrderForm orderForm) throws CosmeticException;

    /**
     * 根据查询条件组合查询订单以及订单明细
     * @param orderForm
     * @return
     * @throws CosmeticException
     */
    BizResult<List<OrderVO>> selectOrdersByConditions(OrderForm orderForm) throws CosmeticException;

    /**
     * 根据订单id查询订单明细
     * @param orderForm
     * @return
     * @throws CosmeticException
     */
    BizResult<OrderVO> selectOrderDetailByOrderId(OrderForm orderForm) throws CosmeticException;
}

