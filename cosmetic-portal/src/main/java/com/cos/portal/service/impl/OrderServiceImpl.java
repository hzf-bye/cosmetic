package com.cos.portal.service.impl;

import com.alibaba.fastjson.JSON;
import com.cos.common.entity.BizResult;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.form.OrderForm;
import com.cos.common.pojo.vo.OrderVO;
import com.cos.common.utils.HttpClientUtil;
import com.cos.common.utils.HttpClientUtils;
import com.cos.portal.service.intf.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 订单service实现类
 * @author: hzf
 * @Date: 2018/4/14 下午8:51
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService{

    @Value("${ORDER_BASE_URL}")
    private String ORDER_BASE_URL;

    @Value("${CREATE_ORDER}")
    private String CREATE_ORDER;

    @Value("${UPDATE_ORDER}")
    private String UPDATE_ORDER;

    @Value("${SELECT_ORDER}")
    private String SELECT_ORDER;

    @Value("${SELECT_ORDER_DETAIL}")
    private String SELECT_ORDER_DETAIL;

    /**
     * 创建订单及订单详情
     *
     * @param orderForm
     * @return
     * @throws CosmeticException
     */
    @Override
    public BizResult<String> createOrder(OrderForm orderForm) throws CosmeticException {

        try {
            //http调用订单层服务
            String json = HttpClientUtils.doPostJson(ORDER_BASE_URL + CREATE_ORDER, JSON.toJSONString(orderForm));
            BizResult bizResult = JSON.parseObject(json, BizResult.class);
            return bizResult;
        } catch (Exception e) {
            log.error("调用订单层创建订单的接口失败", e);
            return BizResult.create("9999", e.getMessage());
        }
    }

    /**
     * 根据订单id更改订单状态
     *
     * @param orderForm
     * @return
     * @throws CosmeticException
     */
    @Override
    public BizResult<Boolean> updateOrderStatusByOrderId(OrderForm orderForm) throws CosmeticException {

        try {
            //http调用订单层服务
            String json = HttpClientUtils.doPostJson(ORDER_BASE_URL + UPDATE_ORDER, JSON.toJSONString(orderForm));
            BizResult bizResult = JSON.parseObject(json, BizResult.class);
            return bizResult;
        } catch (Exception e) {
            log.error("调用订单层创建订单的接口失败", e);
            return BizResult.create("9999", e.getMessage());
        }
    }

    /**
     * 根据查询条件组合查询订单以及订单明细
     *
     * @param orderForm
     * @return
     * @throws CosmeticException
     */
    @Override
    public BizResult<List<OrderVO>> selectOrdersByConditions(OrderForm orderForm) throws CosmeticException {

        try {
            //http调用订单层服务
            String json = HttpClientUtils.doPostJson(ORDER_BASE_URL + SELECT_ORDER, JSON.toJSONString(orderForm));
            BizResult bizResult = JSON.parseObject(json, BizResult.class);
            return bizResult;
        } catch (Exception e) {
            log.error("调用订单层查询订单的接口失败", e);
            return BizResult.create("9999", e.getMessage());
        }
    }

    /**
     * 根据订单id查询订单明细
     *
     * @param orderForm
     * @return
     * @throws CosmeticException
     */
    @Override
    public BizResult<OrderVO> selectOrderDetailByOrderId(OrderForm orderForm) throws CosmeticException {

        try {
            //http调用订单层服务
            String json = HttpClientUtils.doPostJson(ORDER_BASE_URL + SELECT_ORDER_DETAIL, JSON.toJSONString(orderForm));
            BizResult bizResult = JSON.parseObject(json, BizResult.class);
            return bizResult;
        } catch (Exception e) {
            log.error("调用订单层查询订单详情的接口失败", e);
            return BizResult.create("9999", e.getMessage());
        }
    }
}
