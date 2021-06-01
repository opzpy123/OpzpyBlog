package com.opzpy123.mypeojectdemo.controller;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.opzpy123.mypeojectdemo.dto.FileDTO;


import java.io.*;

import com.opzpy123.mypeojectdemo.provider.AliOssProvider;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;


@Controller
public class FileController {

    @Autowired
    private AliOssProvider aliOssProvider;


    //todo oss过期 重新配置一下
    @PostMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");


        //图片上传接口
        try {
            URL url = aliOssProvider.upload(file.getInputStream(), file.getOriginalFilename());
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(1);
            fileDTO.setMessage("上传成功，点击确定按钮即可");
            fileDTO.setUrl(url.toString());
            return fileDTO;
        } catch (IOException e) {
            e.printStackTrace();
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(0);
            fileDTO.setMessage("上传失败，请刷新重试");
            return fileDTO;
        }
    }

}
