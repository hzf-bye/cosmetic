package com.cos.portal.service.intf;

import com.cos.common.entity.BizResult;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.form.CartForm;
import com.cos.common.pojo.vo.CartVO;

import java.util.List;

/**
 * @description: 服务层购物车service
 * @author: hzf
 * @date: 2018/4/8 下午7:31
 */
public interface CartService {

    /**
     * 插入购物车数据
     * @param cartForm
     * @return
     * @throws CosmeticException
     */
    BizResult<List<CartVO>> insertCart(CartForm cartForm) throws CosmeticException;

    /**
     * 批量插入购物车数据
     * @param cartForm
     * @return
     * @throws CosmeticException
     */
    BizResult insertCartList(List<CartForm> cartForms) throws CosmeticException;

    /**
     * 查询购物车数据
     * @param cartForm
     * @return
     * @throws CosmeticException
     */
    BizResult<List<CartVO>> selectCartByConditions(CartForm cartForm) throws CosmeticException;

    /**
     * 更新购物车数据
     * @param cartForm
     * @return
     * @throws CosmeticException
     */
    BizResult<Boolean> updateCartById(CartForm cartForm) throws CosmeticException;

    /**
     * 删除购物车数据
     * @param cartForm
     * @return
     * @throws CosmeticException
     */
    BizResult<Boolean> deleteCartById(CartForm cartForm) throws CosmeticException;

    /**
     * 通过条件删除购物车数据
     * @param cartForm
     * @return
     * @throws CosmeticException
     */
    BizResult<Boolean> deleteCartByConditions(CartForm cartForm) throws CosmeticException;
}
