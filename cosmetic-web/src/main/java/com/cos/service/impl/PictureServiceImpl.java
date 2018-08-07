package com.cos.service.impl;

import com.cos.common.entity.BizResult;
import com.cos.common.enums.ErrorCodeEnum;
import com.cos.common.exception.CosmeticException;
import com.cos.common.utils.FtpUtil;
import com.cos.common.utils.IDUtils;
import com.cos.service.intf.PictureService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description: 上传图片service实现类
 * @author: hzf
 * @date: 2018/2/7 上午9:59
 */
@Service
public class PictureServiceImpl implements PictureService {


    @Value("${FTP_ADDRESS}")
    private String FTP_ADDRESS;
    @Value("${FTP_PORT}")
    private Integer FTP_PORT;
    @Value("${FTP_USER_NAME}")
    private String FTP_USER_NAME;
    @Value("${FTP_PASSWORD}")
    private String FTP_PASSWORD;
    @Value("${FTP_BASE_PATH}")
    private String FTP_BASE_PATH;
    @Value("${IMAGE_BASE_URL}")
    private String IMAGE_BASE_URL;

    /**
     * 上传图片
     *
     * @param file
     * @return
     */
    @Override
    public BizResult uploadPicture(MultipartFile file) throws CosmeticException {

        try {

        //取文件扩展名
        String originalFilename = file.getOriginalFilename();
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        //生成新文件名
        //可以是时间+随机数生成文件名
        String imageName = IDUtils.genImageName();
        //把图片上传到ftp服务器（图片服务器）
        //需要把ftp的参数配置到配置文件中
        //文件在服务器的存放路径，应该使用日期分隔的目录结构
        DateTime dateTime = new DateTime();
        String filePath = dateTime.toString("/yyyy/MM/dd");
        FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USER_NAME, FTP_PASSWORD,
                FTP_BASE_PATH, filePath, imageName + ext, file.getInputStream());

        return BizResult.create(IMAGE_BASE_URL + filePath + "/" + imageName + ext);
        } catch (Exception e) {
            throw new CosmeticException(ErrorCodeEnum.UPLOAD_ERROR);
        }
    }
}
