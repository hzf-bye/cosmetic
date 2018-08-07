package com.cos.service.impl;

import com.cos.common.entity.BizResult;
import com.cos.common.enums.ErrorCodeEnum;
import com.cos.common.enums.OrderStatusEnum;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.form.OrderForm;
import com.cos.common.pojo.vo.OrderDetailVO;
import com.cos.common.pojo.vo.OrderVO;
import com.cos.common.utils.BeanUtil;
import com.cos.common.utils.DateTimeUtil;
import com.cos.common.utils.PriceUtil;
import com.cos.dao.mapper.ext.TbOrderDetailExtMapper;
import com.cos.dao.mapper.ext.TbOrderExtMapper;
import com.cos.dao.pojo.TbOrder;
import com.cos.dao.pojo.TbOrderDetail;
import com.cos.dao.pojo.TbOrderDetailExample;
import com.cos.dao.pojo.TbOrderExample;
import com.cos.service.intf.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @description: 订单service实现类
 * @author: hzf
 * @date: 2018/2/7 下午6:31
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    TbOrderExtMapper orderExtMapper;
    @Autowired
    TbOrderDetailExtMapper orderDetailExtMapper;


    /**
     * 根据查询条件组合查询订单
     *
     * @param orderForm
     * @return
     * @throws CosmeticException
     */
    @Override
    public BizResult<List<OrderVO>> selectOrdersByConditions(OrderForm orderForm) throws CosmeticException {
        try {
            BizResult<List<OrderVO>> bizResult = new BizResult<>();
            PageHelper.startPage(orderForm.getPageNo(), orderForm.getPageSize());
            orderForm.setStartDateTime(DateTimeUtil.strToDate(orderForm.getStartTime()));
            orderForm.setEndDateTime(DateTimeUtil.strToDate(orderForm.getEndTime()));

            List<OrderVO> orderVOS = orderExtMapper.selectOrdersByConditions(orderForm);
            for (OrderVO orderVO : orderVOS) {
                //根据订单id查询订单详情对应商品的短标题
                List<String> goodsNames = orderDetailExtMapper.selectGoodsNameByOrderId(orderVO.getId());
                orderVO.setGoodsNames(goodsNames);
                //将支付金额分转化为元
                orderVO.setPaymentYuan(PriceUtil.formatNoGroup(orderVO.getPayment()));

            }

            if ( CollectionUtils.isEmpty(orderVOS) ) {
                return BizResult.create(Lists.newArrayList());
            }
            PageInfo<OrderVO> pageInfo = new PageInfo<>(orderVOS);
            long total = pageInfo.getTotal();
            bizResult.setData(orderVOS);
            bizResult.setSuccess(true);
            bizResult.setPageNo(orderForm.getPageNo());
            bizResult.setPageSize(orderForm.getPageSize());
            bizResult.setTotalCount((int) total);
            return bizResult;
        } catch (CosmeticException e) {
            throw e;
        } catch (Exception e) {
            throw new CosmeticException(ErrorCodeEnum.QUERY_FAIL, e);
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
            TbOrderExample example = new TbOrderExample();
            example.createCriteria().andOrderIdEqualTo(orderForm.getOrderId());
            TbOrder order = new TbOrder();
            order.setStatus(orderForm.getStatus());
            order.setGmtModified(new Date());
            int count = orderExtMapper.updateByExampleSelective(order, example);
            if (count == 1) {
                return BizResult.create(Boolean.TRUE);
            } else {
                log.error("更新失败 orderId is {}", orderForm.getOrderId());
                throw new CosmeticException(ErrorCodeEnum.PARAM_ERROR);
            }
        } catch (CosmeticException e) {
            throw e;
        } catch (Exception e) {
            throw new CosmeticException(ErrorCodeEnum.EXCEPTION, e);
        }
    }

    /**
     * 根据订单id查询订单明细
     * @param orderId
     * @param pageNo
     * @param pageSize
     * @return
     * @throws CosmeticException
     */
    @Override
    public BizResult<OrderVO> selectOrderDetailByOrderId(String orderId, Integer pageNo, Integer pageSize) throws CosmeticException {

        try {
            //查询订单信息
            TbOrderExample orderExample = new TbOrderExample();
            orderExample.createCriteria().andOrderIdEqualTo(orderId);
            List<TbOrder> tbOrders = orderExtMapper.selectByExample(orderExample);
            OrderVO orderVO = BeanUtil.createCloneBean(tbOrders.get(0), OrderVO.class);
            orderVO.setPaymentYuan(PriceUtil.formatNoGroup(orderVO.getPayment()));
            orderVO.setOrderTime(tbOrders.get(0).getGmtCreat());

            //查询订单明细信息
            PageHelper.startPage(pageNo, pageSize);
            List<OrderDetailVO> orderDetailVOS = orderDetailExtMapper.selectOrderDetailByOrderId(orderVO.getId());
            orderDetailVOS.forEach(orderDetailVO -> {
                orderDetailVO.setPriceYuan(PriceUtil.formatNoGroup(orderDetailVO.getPrice()));
                orderDetailVO.setTotalFeeYuan(PriceUtil.formatNoGroup(orderDetailVO.getTotalFee()));
            });
            PageInfo<OrderDetailVO> pageInfo = new PageInfo<>(orderDetailVOS);
            long total = pageInfo.getTotal();

            orderVO.setOrderDetailVOS(orderDetailVOS);
            BizResult<OrderVO> bizResult = new BizResult<>();
            bizResult.setData(orderVO);
            bizResult.setSuccess(true);
            bizResult.setPageNo(pageNo);
            bizResult.setPageSize(pageSize);
            bizResult.setTotalCount((int) total);
            return BizResult.create(orderVO);
        } catch (Exception e) {
            return BizResult.create(ErrorCodeEnum.EXCEPTION);
        }

    }


}
