package com.cos.service.intf;

import com.cos.common.entity.BizResult;
import com.cos.common.exception.CosmeticException;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description: 上传图片service
 * @author: hzf
 * @date: 2018/2/7 上午9:57
 */
public interface PictureService {

    /**
     *上传图片
     * @param file
     * @return
     */
    BizResult uploadPicture (MultipartFile file) throws CosmeticException;
}
