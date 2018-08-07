package com.cos.rest.service.impl;

import com.alibaba.fastjson.JSON;
import com.cos.common.entity.BizResult;
import com.cos.common.enums.CommonStatusEnum;
import com.cos.common.enums.ErrorCodeEnum;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.form.GoodsForm;
import com.cos.common.pojo.vo.GoodsColorVO;
import com.cos.common.pojo.vo.GoodsVO;
import com.cos.common.utils.BeanUtil;
import com.cos.dao.mapper.TbGoodsColorMapper;
import com.cos.dao.mapper.TbPicResourceMapper;
import com.cos.dao.mapper.ext.TbBrandExtMapper;
import com.cos.dao.mapper.ext.TbGoodsExtMapper;
import com.cos.dao.pojo.*;
import com.cos.rest.service.intf.GoodsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: 商品service实现类
 * @author: hzf
 * @date: 2018/3/8 下午3:03
 */
@Service
@Slf4j
public class GoodsServiceImpl implements GoodsService{

    @Autowired
    TbGoodsExtMapper goodsMapper;

    @Autowired
    private TbBrandExtMapper brandMapper;

    @Autowired
    private TbGoodsColorMapper colorMapper;

    @Autowired
    private TbPicResourceMapper picResourceMapper;

    /**
     * 根据条件查询商品信息
     * @param goodsForm
     * @return
     * @throws CosmeticException
     */
    @Override
    public BizResult<List<GoodsVO>> selectGoodsByConditions(GoodsForm goodsForm) throws CosmeticException {

        try {
            BizResult<List<GoodsVO>> bizResult = new BizResult<>();
            //分页查询
            PageHelper.startPage(goodsForm.getPageNo(), goodsForm.getPageSize());

            TbGoodsExample goodsExample = this.buildSelectGoodsCondition(goodsForm);

            List<TbGoods> tbGoodsList = goodsMapper.selectByExample(goodsExample);

            if (CollectionUtils.isEmpty(tbGoodsList)) {
                return BizResult.create(Lists.newArrayList());
            }

            PageInfo<TbGoods> pageInfo = new PageInfo<>(tbGoodsList);
            long total = pageInfo.getTotal();

            //获取所有的商品id
            List<String> goodsIds = tbGoodsList.stream().map(TbGoods::getGoodsId).collect(Collectors.toList());
            //根据goodsId查询对应的图片资源
            TbPicResourceExample picResourceExample = new TbPicResourceExample();
            picResourceExample.createCriteria().andGoodsIdIn(goodsIds);
            List<TbPicResource> picResources = picResourceMapper.selectByExample(picResourceExample);
            //goodsId, TbPicResource的形式存入map
            Map<String, TbPicResource> tbPicResourceMap = picResources.stream().collect(Collectors.toMap(
                    TbPicResource::getGoodsId, picResource -> picResource));

            List<GoodsVO> goodsVOS = tbGoodsList.stream().map(tbGoods -> {
                GoodsVO goodsVO = BeanUtil.createCloneBean(tbGoods, GoodsVO.class);
                goodsVO.setHeadPic(tbPicResourceMap.get(goodsVO.getGoodsId()).getHeadPic());
                goodsVO.setDetailPic(JSON.parseArray(tbPicResourceMap.get(goodsVO.getGoodsId()).getDetailPic(), String.class));
                return goodsVO;
            }).collect(Collectors.toList());

            //遍历判断商品是否有颜色属性，有则取出相应数据
            for (GoodsVO goodsVO : goodsVOS) {
                //为1代表该商品有按颜色分类
                if (goodsVO.getColor() != null && goodsVO.getColor().equals(1)) {
                    //根据商品编号查询对应颜色以及库存
                    TbGoodsColorExample colorExample = new TbGoodsColorExample();
                    colorExample.createCriteria().andGoodsIdEqualTo(goodsVO.getGoodsId());
                    List<TbGoodsColor> tbGoodsColors = colorMapper.selectByExample(colorExample);
                    //若为空抛出异常
                    if (!CollectionUtils.isEmpty(tbGoodsColors)) {
                        List<GoodsColorVO> goodsColorVOS = Lists.transform(tbGoodsColors,
                                (TbGoodsColor tbGoodsColor) -> BeanUtil.createCloneBean(tbGoodsColor, GoodsColorVO.class));
                        goodsVO.setGoodsColor(goodsColorVOS);
                    } else {
                        throw new CosmeticException(ErrorCodeEnum.NO_DATA);
                    }
                }

            }

            bizResult.setData(goodsVOS);
            bizResult.setPageNo(goodsForm.getPageNo());
            bizResult.setPageSize(goodsForm.getPageSize());
            bizResult.setTotalCount((int)total);
            bizResult.setSuccess(true);
            return bizResult;
        } catch (CosmeticException e) {
            throw e;
        } catch (Exception e) {
            throw new CosmeticException(ErrorCodeEnum.EXCEPTION);
        }
    }

    /**
     * 根据商品编号查询商品
     *
     * @param goodsForm
     * @return
     * @throws CosmeticException
     */
    @Override
    public GoodsVO selectGoodsByGoodsId(GoodsForm goodsForm) throws CosmeticException {
        try {
            String goodsId = goodsForm.getGoodsId();
            TbGoodsExample goodsExample = new TbGoodsExample();
            goodsExample.createCriteria().andGoodsIdEqualTo(goodsId)
                    .andIsDeletedEqualTo(CommonStatusEnum.NO.getCode());

            TbPicResourceExample picResourceExample = new TbPicResourceExample();
            picResourceExample.createCriteria().andGoodsIdEqualTo(goodsId);
            //根据商品编号查询商品与图片资源
            List<TbGoods> tbGoodsList = goodsMapper.selectByExample(goodsExample);
            List<TbPicResource> tbPicResources = picResourceMapper.selectByExample(picResourceExample);
            //若为空抛出异常
            if (!CollectionUtils.isEmpty(tbGoodsList) || !CollectionUtils.isEmpty(tbPicResources)) {
                //若有多条数据抛异常
                if (tbGoodsList.size() > 1 || tbPicResources.size() > 1) {
                    throw new CosmeticException(ErrorCodeEnum.DATA_ERROR);
                }
                List<GoodsColorVO> goodsColorVOS = Lists.newArrayList();
                //为1代表该商品有按颜色分类
                if (tbGoodsList.get(0).getColor() == 1) {
                    //根据商品编号查询对应颜色以及库存
                    TbGoodsColorExample colorExample = new TbGoodsColorExample();
                    colorExample.createCriteria().andGoodsIdEqualTo(goodsId);
                    List<TbGoodsColor> tbGoodsColors = colorMapper.selectByExample(colorExample);
                    //若为空抛出异常
                    if (!CollectionUtils.isEmpty(tbGoodsColors)) {
                        goodsColorVOS = Lists.transform(tbGoodsColors, new Function<TbGoodsColor, GoodsColorVO>() {
                            @Override
                            public GoodsColorVO apply(TbGoodsColor tbGoodsColor) {
                                GoodsColorVO goodsColorVO = BeanUtil.createCloneBean(tbGoodsColor, GoodsColorVO.class);
                                return goodsColorVO;
                            }
                        });
                    } else {
                        throw new CosmeticException(ErrorCodeEnum.NO_DATA);
                    }
                }

                GoodsVO goodsVO = BeanUtil.createCloneBean(tbGoodsList.get(0), GoodsVO.class);
                goodsVO.setHeadPic(tbPicResources.get(0).getHeadPic());
                goodsVO.setGoodsColor(goodsColorVOS);
                goodsVO.setDetailPic(JSON.parseArray(tbPicResources.get(0).getDetailPic(), String.class));
                return goodsVO;
            } else {
                throw new CosmeticException(ErrorCodeEnum.NO_DATA);
            }
        } catch (CosmeticException e) {
            throw e;
        } catch (Exception e) {
            throw new CosmeticException(ErrorCodeEnum.EXCEPTION);
        }
    }

    /**
     * 构造查询商品的查询条件
     * @param goodsForm
     * @return
     */
    private TbGoodsExample buildSelectGoodsCondition(GoodsForm goodsForm){

        TbGoodsExample example = new TbGoodsExample();
        TbGoodsExample.Criteria criteria = example.createCriteria();

        if (goodsForm.getBrandId() != null ) {
            criteria.andBrandIdEqualTo(goodsForm.getBrandId());
        }

        if (!StringUtils.isEmpty(goodsForm.getShopName())) {
            criteria.andShopNameLike("%" + goodsForm.getShopName() + "%");
        }

        return example;

    }
}
