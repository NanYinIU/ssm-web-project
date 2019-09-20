package com.nanyin;

import com.nanyin.config.util.CommonUtil;
import com.nanyin.config.util.MDCUtil;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class test1 {

    public static void main(String[] args) {
        String hashAlgorithmName = "MD5";//加密方式
        Object crdentials = "1";//密码原值
        Object salt = "1";//盐值
        int hashIterations = 1024;//加密1024次
        Object result = new SimpleHash(hashAlgorithmName,crdentials,salt,hashIterations);
        System.out.println("web".getBytes());
    }

    @Test
    public void test2(){
        Date date = new Date();
        String dateString = new SimpleDateFormat("MMddhhmmss-SSS").format(date);
        System.out.println(dateString+"-"+ UUID.randomUUID().toString().replace("-", "").substring(0,10));
        System.out.println(MDCUtil.getLocale().toString());
        System.out.println(CommonUtil.isNull(null));
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


}
