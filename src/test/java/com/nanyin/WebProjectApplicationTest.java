package com.nanyin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by NanYin on 2019/9/6.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WebProjectApplicationTest {

    @Test
    public void testDate(){
        Date currentDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String endTime = simpleDateFormat.format(currentDate);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 0);
        String startTime = simpleDateFormat.format(c.getTime());
        System.out.println(endTime);
        System.out.println(startTime);
    }

    /**
     * 初始化数据
     */
    @Test
    public void initData(){

    }

}
