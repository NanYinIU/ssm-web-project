package com.nanyin.services;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 文件上传至七牛云（头像）工具类
 */
@Component
public class UploadService {

    @Value("${upload.qiniu.ak}")
    private String accessKey;
    @Value("${upload.qiniu.sk}")
    private String secretKey;
    @Value("${upload.qiniu.domain}")
    private String domain;
    @Value("${upload.qiniu.bucket}")
    private String bucket;
    private static final String DOT = ".";

    /**
     * key 可以为空 (null),也可以自定义
     * @param filePath
     * @param key
     * @return
     */
    public String uploadFile(String filePath, String key) {
        String suffix = Files.getFileExtension(filePath);
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(filePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return domain + putRet.key + DOT + suffix;
//            System.out.println(putRet.key);
//            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
            return "";
        }
    }

}
