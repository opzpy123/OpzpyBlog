package com.opzpy123.mypeojectdemo.provider;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.util.Date;

@Service
public class AliOssProvider {
    @Value("${aliyun.oss.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.bucketName}")
    private String bucketName;
    @Value("${aliyun.oss.AccessKey}")
    private String accessKeyId;
    @Value("${aliyun.oss.AccessKeySecret}")
    private String accessKeySecret;


    // <yourObjectName>上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
    public URL upload(InputStream fis,String filename ) {
// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 上传文件流。
        ossClient.putObject(bucketName, filename, fis);

        // 设置URL过期时间为1小时。
        Date expiration = new Date(new Date().getTime() + 15*24*3600 * 1000);
// 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
        URL url = ossClient.generatePresignedUrl(bucketName, filename, expiration);
        System.out.println(url);
// 关闭OSSClient。
        ossClient.shutdown();



    return url;
    }





}
