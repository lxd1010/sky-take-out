package com.sky.controller.admin;

import java.io.IOException;
import java.util.UUID;

import com.sky.constant.MessageConstant;
import com.sky.utils.AliOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sky.result.Result;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/admin/common")
@Slf4j
public class CommonController {


    @Autowired
    private AliOssUtil aliOssUtil;
    /**
     * 上传图片
     * 
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        log.info("上传图片：{}", file);

        try {
            // 原始文件名
            String originalFilename = file.getOriginalFilename();
            // 截取原始文件名的后缀
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 构造新文件名称
            String objectName = UUID.randomUUID().toString() + extension;

            // 文件的请求路径
            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
            return Result.success(filePath);
        } catch (IOException e) {
            log.info("上传图片失败：{}", e);
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
    // @PostMapping("/upload")
    // public Result<String> upload(MultipartFile file) {
    //     log.info("上传图片：{}", file);
    //     String originalFilename = file.getOriginalFilename();
    //     String suffix = ".jpg";
    //     if (originalFilename != null) {
    //         suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
    //     }
    //     String fileName = UUID.randomUUID().toString() + suffix;
    //     String imgUrl = "http://localhost/media/" + fileName;
    //     try {
    //         file.transferTo(new java.io.File("D:\\Variable\\nginx-1.24.0\\media\\" + fileName));
    //         return Result.success(imgUrl);
    //     } catch (IllegalStateException | IOException e) {
    //         log.error("上传图片失败：{}", e);
    //         return Result.error("上传图片失败");
    //     }
    // }
}
