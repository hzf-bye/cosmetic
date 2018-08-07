package com.cos.service.impl;

import com.alibaba.fastjson.JSON;
import com.cos.common.entity.BizResult;
import com.cos.common.enums.CommonStatusEnum;
import com.cos.common.enums.ErrorCodeEnum;
import com.cos.common.exception.CosmeticException;
import com.cos.common.pojo.form.GoodsColorForm;
import com.cos.common.pojo.form.GoodsForm;
import com.cos.common.pojo.vo.GoodsColorVO;
import com.cos.common.pojo.vo.GoodsVO;
import com.cos.common.utils.BeanUtil;
import com.cos.common.utils.IDUtils;
import com.cos.dao.mapper.TbBrandMapper;
import com.cos.dao.mapper.TbGoodsColorMapper;
import com.cos.dao.mapper.TbPicResourceMapper;
import com.cos.dao.mapper.ext.TbBrandExtMapper;
import com.cos.dao.mapper.ext.TbGoodsExtMapper;
import com.cos.dao.pojo.*;
import com.cos.pojo.request.GoodsRequest;
import com.cos.service.intf.GoodsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 商品service
 * @author: hzf
 * @date: 2018/2/4 下午5:48
 */
@Service
@Slf4j
public class GoodsServiceImpl implements GoodsService {


    @Autowired
    private TbGoodsExtMapper goodsMapper;

    @Autowired
    private TbBrandExtMapper brandMapper;

    @Autowired
    private TbGoodsColorMapper colorMapper;

    @Autowired
    private TbPicResourceMapper picResourceMapper;

    /**
     * 插入单个商品记录
     *
     * @param goodsForm
     * @throws CosmeticException
     */
    @Override
    public BizResult<String> insertGoods(GoodsForm goodsForm) throws CosmeticException {

        try {
            //生成商品编号
            String goodsId = String.valueOf(IDUtils.getGoodsId());
            //将goodsForm拷贝至TbGoods
            TbGoods tbGoods = BeanUtil.createCloneBean(goodsForm, TbGoods.class);
            tbGoods.setGoodsId(goodsId);
            tbGoods.setGmtCreat(new Date());
            tbGoods.setGmtModified(new Date());
            //向图片资源表中插入商品对应的图片
            TbPicResource tbPicResource = new TbPicResource();
            tbPicResource.setGoodsId(goodsId);
            tbPicResource.setHeadPic(goodsForm.getHeadPic());
            tbPicResource.setDetailPic(JSON.toJSONString(goodsForm.getDetailPic()));
            tbPicResource.setGmtCreat(new Date());
            tbPicResource.setGmtModified(new Date());
            picResourceMapper.insertSelective(tbPicResource);
            //若商品有颜色属性则插入颜色相关数据至数据库中
            //不为空则代表有颜色属性
            List<GoodsColorForm> goodsColor = goodsForm.getGoodsColor();
            if (!CollectionUtils.isEmpty(goodsColor)) {
                //设置商品表color标识为1，代表有数据，默认值是0
                tbGoods.setColor(1);
                TbGoodsColor tbGoodsColor = new TbGoodsColor();
                for (GoodsColorForm goodsColorForm : goodsColor) {
                    tbGoodsColor.setGoodsId(goodsId);
                    tbGoodsColor.setInventory(goodsColorForm.getInventory());
                    tbGoodsColor.setColorName(goodsColorForm.getColorName());
                    tbGoodsColor.setGmtCreat(new Date());
                    tbGoodsColor.setGmtModified(new Date());
                    colorMapper.insertSelective(tbGoodsColor);
                }
            }
            //向商品表中插入商品数据插入商品数据
            goodsMapper.insertSelective(tbGoods);
            return BizResult.create(goodsId);
        } catch (Exception e) {
            throw new CosmeticException(ErrorCodeEnum.EXCEPTION);
        }
    }

    /**
     * 根据商品编号删除商品
     *
     * @param goodsId
     * @throws CosmeticException
     */
    @Override
    public void deleteGoodsByGoodsID(String goodsId) throws CosmeticException {
        try {
            //删除商品对应的图片资源
            TbPicResourceExample picResourceExample = new TbPicResourceExample();
            picResourceExample.createCriteria().andGoodsIdEqualTo(goodsId);
            picResourceMapper.deleteByExample(picResourceExample);
            //删除商品对应的颜色信息
            TbGoodsColorExample colorExample = new TbGoodsColorExample();
            colorExample.createCriteria().andGoodsIdEqualTo(goodsId);
            colorMapper.deleteByExample(colorExample);
            //删除商品信息
            TbGoodsExample goodsExample = new TbGoodsExample();
            goodsExample.createCriteria().andGoodsIdEqualTo(goodsId);
            goodsMapper.deleteByExample(goodsExample);
        } catch (Exception e) {
            throw new CosmeticException(ErrorCodeEnum.EXCEPTION);
        }
    }

    /**
     * 商品名模糊查询商品
     *
     * @param name
     * @param pageNo
     * @param pageSize
     * @return
     * @throws CosmeticException
     */
    @Override
    public BizResult<List<GoodsVO>> selectGoodsByName(String name, Integer pageNo, Integer pageSize) throws CosmeticException {

        try {
            BizResult<List<GoodsVO>> bizResult = new BizResult();
            //分页处理
            PageHelper.startPage(pageNo, pageSize);
            //商品名模糊查询
            List<GoodsVO> goodsVOS = goodsMapper.selectGoodsByName("%" + name + "%");
            //将tbGoodsList拷贝至goodsVos
            if (!CollectionUtils.isEmpty(goodsVOS)) {
                //取total
                PageInfo<GoodsVO> pageInfo = new PageInfo<>(goodsVOS);
                long total = pageInfo.getTotal();
                //将detailPicJson转化为List<String> detailPic
                for (GoodsVO goodsVO : goodsVOS) {
                    goodsVO.setDetailPic(JSON.parseArray(goodsVO.getDetailPicJson(), String.class));
                }
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
                            List<GoodsColorVO> goodsColorVOS = Lists.transform(tbGoodsColors, new Function<TbGoodsColor, GoodsColorVO>() {
                                @Override
                                public GoodsColorVO apply(TbGoodsColor tbGoodsColor) {
                                    GoodsColorVO goodsColorVO = BeanUtil.createCloneBean(tbGoodsColor, GoodsColorVO.class);
                                    return goodsColorVO;
                                }
                            });
                            goodsVO.setGoodsColor(goodsColorVOS);
                        } else {
                            throw new CosmeticException(ErrorCodeEnum.NO_DATA);
                        }
                    }

                }
                bizResult.setData(goodsVOS);
                bizResult.setSuccess(true);
                bizResult.setPageNo(pageNo);
                bizResult.setPageSize(pageSize);
                bizResult.setTotalCount((int) total);
                return bizResult;
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
     * 根据品牌名查询商品
     *
     * @param name
     * @param pageNo
     * @param pageSize
     * @return
     * @throws CosmeticException
     */
    @Override
    public BizResult<List<GoodsVO>> selectGoodsByBrand(String name, Integer pageNo, Integer pageSize) throws CosmeticException {
        try {
            BizResult<List<GoodsVO>> bizResult = new BizResult();
            //分页处理
            PageHelper.startPage(pageNo, pageSize);
            List<GoodsVO> goodsVOS = goodsMapper.selectGoodsByBrandName(name);
            if (!CollectionUtils.isEmpty(goodsVOS)) {
                //取total
                PageInfo<GoodsVO> pageInfo = new PageInfo<>(goodsVOS);
                long total = pageInfo.getTotal();
                //将detailPicJson转化为List<String> detailPic
                for (GoodsVO goodsVO : goodsVOS) {
                    goodsVO.setDetailPic(JSON.parseArray(goodsVO.getDetailPicJson(), String.class));
                }
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
                            List<GoodsColorVO> goodsColorVOS = Lists.transform(tbGoodsColors, new Function<TbGoodsColor, GoodsColorVO>() {
                                @Override
                                public GoodsColorVO apply(TbGoodsColor tbGoodsColor) {
                                    GoodsColorVO goodsColorVO = BeanUtil.createCloneBean(tbGoodsColor, GoodsColorVO.class);
                                    return goodsColorVO;
                                }
                            });
                            goodsVO.setGoodsColor(goodsColorVOS);
                        } else {
                            throw new CosmeticException(ErrorCodeEnum.NO_DATA);
                        }
                    }

                }
                bizResult.setData(goodsVOS);
                bizResult.setSuccess(true);
                bizResult.setPageNo(pageNo);
                bizResult.setPageSize(pageSize);
                bizResult.setTotalCount((int) total);
                return bizResult;
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
     * 查询所有产品
     *
     * @param pageNo
     * @param pageSize
     * @return
     * @throws CosmeticException
     */
    @Override
    public BizResult<List<GoodsVO>> selectAllGoods(Integer pageNo, Integer pageSize) throws CosmeticException {
        try {
            BizResult<List<GoodsVO>> bizResult = new BizResult();
            //分页处理
            PageHelper.startPage(pageNo, pageSize);
            List<GoodsVO> goodsVOS = goodsMapper.selectAllGoods();
            if (!CollectionUtils.isEmpty(goodsVOS)) {
                //取total
                PageInfo<GoodsVO> pageInfo = new PageInfo<>(goodsVOS);
                long total = pageInfo.getTotal();
                //将detailPicJson转化为List<String> detailPic
                for (GoodsVO goodsVO : goodsVOS) {
                    goodsVO.setDetailPic(JSON.parseArray(goodsVO.getDetailPicJson(), String.class));
                }
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
                            List<GoodsColorVO> goodsColorVOS = Lists.transform(tbGoodsColors, new Function<TbGoodsColor, GoodsColorVO>() {
                                @Override
                                public GoodsColorVO apply(TbGoodsColor tbGoodsColor) {
                                    GoodsColorVO goodsColorVO = BeanUtil.createCloneBean(tbGoodsColor, GoodsColorVO.class);
                                    return goodsColorVO;
                                }
                            });
                            goodsVO.setGoodsColor(goodsColorVOS);
                        } else {
                            throw new CosmeticException(ErrorCodeEnum.NO_DATA);
                        }
                    }

                }
                bizResult.setData(goodsVOS);
                bizResult.setSuccess(true);
                bizResult.setPageNo(pageNo);
                bizResult.setPageSize(pageSize);
                bizResult.setTotalCount((int) total);
                return bizResult;
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
     * 根据商品编号查询商品
     *
     * @param goodsId
     * @return
     * @throws CosmeticException
     */
    @Override
    public GoodsVO selectGoodsByGoodsId(String goodsId) throws CosmeticException {
        try {
            TbGoodsExample goodsExample = new TbGoodsExample();
            goodsExample.createCriteria().andGoodsIdEqualTo(goodsId)
                    .andIsDeletedEqualTo(CommonStatusEnum.NO.getCode());

            TbPicResourceExample picResourceExample = new TbPicResourceExample();
            picResourceExample.createCriteria().andGoodsIdEqualTo(goodsId);

            //查询品牌名
            GoodsForm form = new GoodsForm();
            form.setGoodsId(goodsId);
            List<GoodsVO> goodsVOS = goodsMapper.selectGoodsByConditions(form);

            //根据商品编号查询商品与图片资源与品牌名字
            List<TbGoods> tbGoodsList = goodsMapper.selectByExample(goodsExample);
            List<TbPicResource> tbPicResources = picResourceMapper.selectByExample(picResourceExample);
            //若为空抛出异常
            if (!CollectionUtils.isEmpty(tbGoodsList) || !CollectionUtils.isEmpty(tbPicResources)
                    || !CollectionUtils.isEmpty(goodsVOS)) {
                //若有多条数据抛异常
                if (tbGoodsList.size() > 1 || tbPicResources.size() > 1 || goodsVOS.size() > 1) {
                    throw new CosmeticException(ErrorCodeEnum.DATA_ERROR);
                }
                List<GoodsColorVO> goodsColorVOS = Lists.newArrayList();
                //为1代表该商品有按颜色分类
                if (tbGoodsList.get(0).getColor() != null && tbGoodsList.get(0).getColor().equals(1)) {
                    //根据商品编号查询对应颜色以及库存
                    TbGoodsColorExample colorExample = new TbGoodsColorExample();
                    colorExample.createCriteria().andGoodsIdEqualTo(goodsId);
                    List<TbGoodsColor> tbGoodsColors = colorMapper.selectByExample(colorExample);
                    //若为空抛出异常
                    if (!CollectionUtils.isEmpty(tbGoodsColors)) {
//                        goodsColorVOS = Lists.transform(tbGoodsColors, new Function<TbGoodsColor, GoodsColorVO>() {
//                            @Override
//                            public GoodsColorVO apply(TbGoodsColor tbGoodsColor) {
//                                GoodsColorVO goodsColorVO = BeanUtil.createCloneBean(tbGoodsColor, GoodsColorVO.class);
//                                return goodsColorVO;
//                            }
//                        });
                        goodsColorVOS = tbGoodsColors.stream().map(tbGoodsColor -> {
                            GoodsColorVO goodsColorVO = BeanUtil.createCloneBean(tbGoodsColor, GoodsColorVO.class);
                            return goodsColorVO;

                        }).collect(Collectors.toList());
                    } else {
                        throw new CosmeticException(ErrorCodeEnum.NO_DATA);
                    }
                }

                GoodsVO goodsVO = BeanUtil.createCloneBean(tbGoodsList.get(0), GoodsVO.class);
                goodsVO.setHeadPic(tbPicResources.get(0).getHeadPic());
                goodsVO.setGoodsColor(goodsColorVOS);
                goodsVO.setBrandName(goodsVOS.get(0).getBrandName());
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
     * 根据查询条件组合查询商品
     *
     * @param request
     * @return
     */
    @Override
    public BizResult<List<GoodsVO>> selectGoodsByConditions(GoodsRequest request) throws CosmeticException {
        try {
            BizResult<List<GoodsVO>> bizResult = new BizResult();
            //分页处理
            PageHelper.startPage(request.getPageNo(), request.getPageSize());
            GoodsForm form = BeanUtil.createCloneBean(request, GoodsForm.class);
            List<GoodsVO> goodsVOS = goodsMapper.selectGoodsByConditions(form);
            if (!CollectionUtils.isEmpty(goodsVOS)) {
                //取total
                PageInfo<GoodsVO> pageInfo = new PageInfo<>(goodsVOS);
                long total = pageInfo.getTotal();
                //将detailPicJson转化为List<String> detailPic
                for (GoodsVO goodsVO : goodsVOS) {
                    goodsVO.setDetailPic(JSON.parseArray(goodsVO.getDetailPicJson(), String.class));
                }
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
//                            List<GoodsColorVO> goodsColorVOS = tbGoodsColors.stream().map(tbGoodsColor -> {
//                                GoodsColorVO goodsColorVO = BeanUtil.createCloneBean(tbGoodsColor, GoodsColorVO.class);
//                                return goodsColorVO;
//
//                            }).collect(Collectors.toList());
//                            goodsVO.setGoodsColor(goodsColorVOS);
                        } else {
                            throw new CosmeticException(ErrorCodeEnum.NO_DATA);
                        }
                    }

                }
                bizResult.setData(goodsVOS);
                bizResult.setSuccess(true);
                bizResult.setPageNo(request.getPageNo());
                bizResult.setPageSize(request.getPageSize());
                bizResult.setTotalCount((int) total);
                return bizResult;
            } else {
                bizResult.setData(Lists.newArrayList());
                bizResult.setSuccess(true);
                return bizResult;
            }
        } catch (CosmeticException e) {
            throw e;
        } catch (Exception e) {
            throw new CosmeticException(ErrorCodeEnum.EXCEPTION);
        }
    }

    /**
     * 根据商品编号更新商品信息
     *
     * @param request
     * @throws CosmeticException
     */
    @Override
    public void updateGoodsByGoodsId(GoodsRequest request) throws CosmeticException {
        try {
            //将 goodsForm拷贝至tbGoods
            TbGoods tbGoods = BeanUtil.createCloneBean(request, TbGoods.class);
            tbGoods.setGmtModified(new Date());
            TbGoodsExample example = new TbGoodsExample();
            example.createCriteria().andGoodsIdEqualTo(request.getGoodsId());
            //根据商品编号更新商品信息
            //商品颜色数据不为空表示有颜色属性
            if (!CollectionUtils.isEmpty(request.getGoodsColor())) {
                tbGoods.setColor(1);
            }
            goodsMapper.updateByExampleSelective(tbGoods, example);
            //更新商品对应的图片资源
            TbPicResource tbPicResource = new TbPicResource();
            tbPicResource.setGmtModified(new Date());
            tbPicResource.setHeadPic(request.getHeadPic());
            if (request.getDetailPic() != null) {
                tbPicResource.setDetailPic(JSON.toJSONString(request.getDetailPic()));
            }
            TbPicResourceExample picResourceExample = new TbPicResourceExample();
            picResourceExample.createCriteria().andGoodsIdEqualTo(request.getGoodsId());
            picResourceMapper.updateByExampleSelective(tbPicResource, picResourceExample);
            //判断商品是否有颜色属性，有颜色属性也需要更新数据
            if (!StringUtils.isEmpty(request.getGoodsColor())) {
                List<GoodsColorForm> GoodsColor = request.getGoodsColor();
                if (!CollectionUtils.isEmpty(GoodsColor)) {
                    for (GoodsColorForm goodsColorForm : GoodsColor) {
                        //先查询数据库中是够有对应商品的对应颜色
                        //若有则直接设置该颜色对应库存，若没有则添加新颜色及库存
                        TbGoodsColorExample colorExample = new TbGoodsColorExample();
                        colorExample.createCriteria().andGoodsIdEqualTo(request.getGoodsId())
                                .andColorNameEqualTo(goodsColorForm.getColorName());
                        List<TbGoodsColor> tbGoodsColors = colorMapper.selectByExample(colorExample);
                        //若不空则代表有该数据，直接设置库存即可
                        if (!CollectionUtils.isEmpty(tbGoodsColors)) {
                            TbGoodsColor tbGoodsColor = new TbGoodsColor();
                            tbGoodsColor.setInventory(goodsColorForm.getInventory());
                            tbGoodsColor.setGmtModified(new Date());
                            colorMapper.updateByExampleSelective(tbGoodsColor, colorExample);
                        } else {
                            //需要添加新数据
                            TbGoodsColor tbGoodsColor = new TbGoodsColor();
                            tbGoodsColor.setInventory(goodsColorForm.getInventory());
                            tbGoodsColor.setColorName(goodsColorForm.getColorName());
                            tbGoodsColor.setGoodsId(request.getGoodsId());
                            tbGoodsColor.setGmtModified(new Date());
                            tbGoodsColor.setGmtCreat(new Date());
                            colorMapper.insertSelective(tbGoodsColor);
                        }
                    }
                }
            }

        } catch (CosmeticException e) {
            throw e;
        } catch (Exception e) {
            throw new CosmeticException(ErrorCodeEnum.EXCEPTION);
        }
    }


}
