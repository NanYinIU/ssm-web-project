package com.nanyin;

import com.nanyin.config.quartz.service.JobService;
import com.nanyin.services.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(5);
        list.forEach(x -> {
            System.out.println(x);
        });
    }

    @Test
    public void testStingFormat(){
        String format = String.format("%s:%s", "x", "y");
        System.out.println(format);
    }

    @Autowired
    JobService jobService;
    @Test
    public void TestQuartzJobs(){
        try {
            jobService.openAssignJob(2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Autowired
    RedisService redisService ;

    @Test
    public void test8(){

        System.out.println(redisService.exists("2b1714e3-65d0-461e-aa41-021d078c00c7"));;
        System.out.println(redisService.get("2b1714e3-65d0-461e-aa41-021d078c00c7"));
    }

}
