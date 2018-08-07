package com.cos.dao.mapper.ext;

import com.cos.common.pojo.form.OrderForm;
import com.cos.common.pojo.vo.OrderVO;
import com.cos.dao.mapper.TbOrderMapper;

import java.util.List;

/**
 * @description:
 * @author: hzf
 * @date: 2018/2/7 下午6:37
 */
public interface TbOrderExtMapper extends TbOrderMapper {

    /**
     * 根据查询条件组合查询订单
     * @param orderForm
     * @return
     */
    List<OrderVO> selectOrdersByConditions(OrderForm orderForm);
}
