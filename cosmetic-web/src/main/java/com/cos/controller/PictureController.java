package com.cos.controller;

import com.cos.common.entity.BizResult;
import com.cos.common.exception.CosmeticException;
import com.cos.service.intf.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description: 上传图片controller
 * @author: hzf
 * @date: 2018/2/7 上午10:15
 */
@Controller
@Slf4j
@RequestMapping("/picture")
public class PictureController {

    @Autowired
    PictureService pictureService;

    @ResponseBody
    @RequestMapping("upload")
    public BizResult uploadFile(MultipartFile file) {
        if ( null == file || file.isEmpty()) {
            return BizResult.create("9999", "上传的文件为空");
        }

        try {
            BizResult bizResult = pictureService.uploadPicture(file);
            return bizResult;
        } catch (CosmeticException e) {
            log.error("上传文件失败, file is {}", file, e);
            return BizResult.create(e.getCode(), e.getMessage());
        }
    }

}
