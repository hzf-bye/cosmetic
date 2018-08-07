package com.cos.order.service.impl;

import com.cos.common.entity.BizResult;
import com.cos.common.enums.ErrorCodeEnum;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.form.OrderDetailForm;
import com.cos.common.pojo.form.OrderForm;
import com.cos.common.pojo.vo.OrderDetailVO;
import com.cos.common.pojo.vo.OrderVO;
import com.cos.common.utils.*;
import com.cos.dao.mapper.TbCartMapper;
import com.cos.dao.mapper.TbGoodsColorMapper;
import com.cos.dao.mapper.ext.TbGoodsExtMapper;
import com.cos.dao.mapper.ext.TbOrderDetailExtMapper;
import com.cos.dao.mapper.ext.TbOrderExtMapper;
import com.cos.dao.pojo.*;
import com.cos.order.service.intf.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: 订单service实现类
 * @author: hzf
 * @date: 2018/4/11 下午7:34
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private TbOrderExtMapper orderExtMapper;

    @Autowired
    private TbOrderDetailExtMapper orderDetailExtMapper;

    @Autowired
    private TbGoodsExtMapper goodsExtMapper;

    @Autowired
    private TbGoodsColorMapper goodsColorMapper;

    @Autowired
    private TbCartMapper cartMapper;

    /**
     * 创建订单及订单详情
     * @param orderForm
     * @return
     * @throws CosmeticException
     */
    @Override
    public BizResult<String> createOrder(OrderForm orderForm) throws CosmeticException{

        try {
            TbOrder tbOrder = BeanUtil.createCloneBean(orderForm, TbOrder.class);
            tbOrder.setOrderId(IDUtils.getOrderId());
            tbOrder.setPayment(orderForm.getPayment());
            tbOrder.setGmtCreat(new Date());
            tbOrder.setGmtModified(new Date());
            //创建订单
            orderExtMapper.insertSelective(tbOrder);
            Long id = tbOrder.getId();
            List<OrderDetailForm> orderDetailForms = orderForm.getOrderDetailForms();
            List<TbOrderDetail> tbOrderDetails = orderDetailForms.stream().map(orderDetailForm -> {
                TbOrderDetail tbOrderDetail = BeanUtil.createCloneBean(orderDetailForm, TbOrderDetail.class);
                tbOrderDetail.setGmtCreat(new Date());
                tbOrderDetail.setGmtModified(new Date());
                tbOrderDetail.setOrderId(id);
                long totalPrice = orderDetailForm.getPrice() * orderDetailForm.getNum();
                tbOrderDetail.setTotalFee(totalPrice);
                return  tbOrderDetail;
            }).collect(Collectors.toList());

            //创建订单详情
            tbOrderDetails.forEach(tbOrderDetail -> {
                orderDetailExtMapper.insertSelective(tbOrderDetail);
            });

            //创建订单详情后需要减少对对应商品的库存
            orderDetailForms.forEach(orderDetailForm -> {
                if (StringUtils.isBlank(orderDetailForm.getColor())) {

                    //商品颜色属性为空，则去改变对应商品表的库存
                    TbGoodsExample example = new TbGoodsExample();
                    example.createCriteria().andGoodsIdEqualTo(orderDetailForm.getGoodsId());
                    //先取出商品极其颜色对应的库存
                    List<TbGoods> tbGoods = goodsExtMapper.selectByExample(example);
                    long inventory = tbGoods.get(0).getInventory();
                    //设置现有库存
                    TbGoods goods = new TbGoods();
                    goods.setInventory(inventory - orderDetailForm.getNum());
                    goodsExtMapper.updateByExampleSelective(goods, example);
                } else {

                    //商品颜色属性不为空，则去改变对应商品颜色表的库存
                    TbGoodsColorExample example = new TbGoodsColorExample();
                    example.createCriteria().andGoodsIdEqualTo(orderDetailForm.getGoodsId())
                            .andColorNameEqualTo(orderDetailForm.getColor());
                    //先取出商品极其颜色对应的库存
                    List<TbGoodsColor> goodsColors = goodsColorMapper.selectByExample(example);
                    long inventory = goodsColors.get(0).getInventory();
                    //设置现有库存
                    TbGoodsColor goodsColor = new TbGoodsColor();
                    goodsColor.setInventory(inventory - orderDetailForm.getNum());
                    goodsColorMapper.updateByExampleSelective(goodsColor, example);
                }

            });


            //创建订单后需要将购物车删除
            //根据userId与meetingPlaceId删除购物车
            TbCartExample cartExample = new TbCartExample();
            cartExample.createCriteria().andUserIdEqualTo(orderForm.getUserId())
                    .andMeetingPlaceIdEqualTo(orderForm.getMeetingPlaceId());
            cartMapper.deleteByExample(cartExample);

            return BizResult.create(tbOrder.getOrderId());
        } catch (Exception e) {
            throw new CosmeticException(ErrorCodeEnum.EXCEPTION, e);
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
            TbOrder order = BeanUtil.createCloneBean(orderForm, TbOrder.class);
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
     * 根据查询条件组合查询订单以及订单明细
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

            List<TbOrder> tbOrders = orderExtMapper.selectByExample(this.buildOrderCondition(orderForm));
            PageInfo<TbOrder> pageInfo = new PageInfo<>(tbOrders);
            long total = pageInfo.getTotal();

            List<OrderVO> orderVOS = tbOrders.stream().map(tbOrder -> {
                OrderVO orderVO = BeanUtil.createCloneBean(tbOrder, OrderVO.class);
                orderVO.setOrderTime(tbOrder.getGmtCreat());
                orderVO.setOrderModifiedTime(tbOrder.getGmtModified());
                //根据订单id查询订单详情
                TbOrderDetailExample orderDetailExample = new TbOrderDetailExample();
                orderDetailExample.createCriteria().andOrderIdEqualTo(orderVO.getId());
                List<TbOrderDetail> orderDetails = orderDetailExtMapper.selectByExample(orderDetailExample);
                List<OrderDetailVO> orderDetailVOS = Lists.transform(orderDetails,
                        (TbOrderDetail orderDetail) -> {
                            //根据goodsId查询商品短标题
                            TbGoodsExample goodsExample = new TbGoodsExample();
                            goodsExample.createCriteria().andGoodsIdEqualTo(orderDetail.getGoodsId());
                            List<TbGoods> goodsList = goodsExtMapper.selectByExample(goodsExample);
                            OrderDetailVO orderDetailVO =  BeanUtil.createCloneBean(orderDetail, OrderDetailVO.class);
                            orderDetailVO.setGoodsName(goodsList.get(0).getShortName());
                            orderDetailVO.setMainPic(goodsList.get(0).getMainPic());
                            return orderDetailVO;
                        });
                orderVO.setOrderDetailVOS(orderDetailVOS);
                return orderVO;

            }).collect(Collectors.toList());

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
            orderVO.setOrderModifiedTime(tbOrders.get(0).getGmtModified());
            orderVO.setRemark(tbOrders.get(0).getBuyyerRemark());

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


        /**
         * 构造查询订单的查询条件
         *
         * @param orderForm
         * @return
         */
    private TbOrderExample buildOrderCondition(OrderForm orderForm){

        TbOrderExample example = new TbOrderExample();
        TbOrderExample.Criteria criteria = example.createCriteria();

        if (orderForm.getStatus() != null) {
            criteria.andStatusEqualTo(orderForm.getStatus());
        }
        if (StringUtils.isNotBlank(orderForm.getUserId())) {
            criteria.andUserIdEqualTo(orderForm.getUserId());
        }
        if (orderForm.getMeetingPlaceId() != null) {
            criteria.andMeetingPlaceIdEqualTo(orderForm.getMeetingPlaceId());
        }
        if (!CollectionUtils.isEmpty(orderForm.getOrderStatus())) {
            criteria.andStatusIn(orderForm.getOrderStatus());
        }
        example.setOrderByClause("tb_order.gmt_creat desc");
        return example;
    }

}


