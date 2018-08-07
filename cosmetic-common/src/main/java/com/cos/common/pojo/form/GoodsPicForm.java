package com.cos.common.pojo.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * @description: 商品图片form
 * @author: hzf
 * @date: 2018/2/6 上午10:45
 */
@Data
public class GoodsPicForm {

    /**
     * 商品详情页头部图片
     */
    private String headPic;

    /**
     * 商品详情页详情图
     */
    private String detailPic;

}
