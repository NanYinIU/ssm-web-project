package com.nanyin.services;

import com.nanyin.entity.Calendar;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by NanYin on 2017-08-15 下午10:39.
 * 包名： com.nanyin.services
 * 类描述：
 */
@Service
public interface CalendarService {
    List<Calendar> findEventByUserName(String name);

    int insertEventByUserName(Calendar calendar);

    int updateEvent(Calendar calendar);

    int delectEvent(int id);

    int selectUnfinshEvents(String name);
}
