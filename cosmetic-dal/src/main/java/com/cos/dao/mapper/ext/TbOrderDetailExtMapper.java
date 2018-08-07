package com.cos.dao.mapper.ext;

import com.cos.common.pojo.vo.OrderDetailVO;
import com.cos.dao.mapper.TbOrderDetailMapper;

import java.util.List;

/**
 * @description:
 * @author: hzf
 * @date: 2018/2/7 下午7:23
 */
public interface TbOrderDetailExtMapper extends TbOrderDetailMapper {

    List<OrderDetailVO> selectOrderDetailByOrderId(Long orderId);

    List<String> selectGoodsNameByOrderId(Long orderId);
}
