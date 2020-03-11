package com.nanyin;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.nanyin.config.util.CommonUtils;
import com.nanyin.config.util.MDCUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;



public class test1 {

    public static void main(String[] args) {
        String hashAlgorithmName = "MD5";//加密方式
        Object crdentials = "123456";//密码原值
        Object salt = "1";//盐值
        int hashIterations = 1024;//加密1024次
        SimpleHash simpleHash = new SimpleHash(hashAlgorithmName, crdentials, salt, hashIterations);
        System.out.println(simpleHash.toHex());
    }

    @Test
    public void test2(){
        Date date = new Date();
        String dateString = new SimpleDateFormat("MMddhhmmss-SSS").format(date);
        System.out.println(dateString+"-"+ UUID.randomUUID().toString().replace("-", "").substring(0,10));
        System.out.println(MDCUtil.getLocale().toString());
        System.out.println(CommonUtils.isNull(null));
    }

    @Test
    public void test3(){
        String string = "abcdefghigklmnopqrst";
        char[] array = string.toCharArray();
        for (int i = 0; i < (array.length/7 + 1); i++) {
            i = i + 1;
            for (int j = (i - 1) * 7; j < i * 7 && j<array.length ;j++) {
                System.out.print(array[j]);
            }
            System.out.println("\n");
        }
    }

    @Test
    public void test4(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

        Calendar c2 = Calendar.getInstance();
        c2.set(Calendar.DAY_OF_MONTH, 0);

        c2.set(Calendar.HOUR_OF_DAY, -1);
        //将分钟至0
        c2.set(Calendar.MINUTE, -1);
        //将秒至0
        c2.set(Calendar.SECOND,-1);
        //将毫秒至0
        c2.set(Calendar.MILLISECOND, -1);

        String endTime = simpleDateFormat.format(c2.getTime());
        System.out.println(endTime);
    }

    @Test
    public void test5(){
        try {
            Class<?> clazz = Class.forName("com.nanyin.config.quartz.task.SimpleTask");
            for (Class<?> aClass : clazz.getInterfaces()) {
                if(aClass.equals(Class.forName("com.nanyin.config.quartz.task.Task"))){
                    System.out.println(true);
                    break;
                }
            }
            Method execute = clazz.getDeclaredMethod("execute");
            System.out.println(execute.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test6(){
        // String 转化为 byte数组
        String hello = "hello world";
        byte[] bytes = hello.getBytes();
        // byte数组转化为 String
        String newHello = new String(bytes);
        System.out.println(newHello);
        System.out.println(UUID.randomUUID().toString() );
    }

    @Test
    public void testUploadQiNiu() {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = "hZKOhv9GXfOztS-dX8AFwmmR6g-AbfCxuLO9JZ5x";
        String secretKey = "VcpO6QSNxGInrgyMMr_8_qMI3W8jZZChDw2hCIon";
        String url = "http://q61syra66.bkt.clouddn.com/";
        String bucket = "nanyiniu";
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "https://raw.githubusercontent.com/NanYinIU/PicRoom/master/img/20200214112801.png";
        String key = "abcdfefefe";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

    @Test
    public void testFileName(){
        String filePath = "/Users/gaoguoxing/Downloads/Jietu20200221-183204.jpg.txt";
        String suffix = Files.getFileExtension(filePath);
        System.out.println(suffix);
    }

    @Test
    public void testString(){
       String s = "123456";
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode(s));;

    }



}
