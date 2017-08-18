package com.nanyin.services.impl;

import com.nanyin.entity.Calendar;
import com.nanyin.mapper.CalendarMapper;
import com.nanyin.services.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by NanYin on 2017-08-15 下午10:39.
 * 包名： com.nanyin.services.impl
 * 类描述：
 */
public class CalendarServiceImpl implements CalendarService {
    @Autowired
    private CalendarMapper calendarMapper;

    public List<Calendar> findEventByUserName(String name) {
        return calendarMapper.findEventByUserName(name);
    }

    public int insertEventByUserName(Calendar calendar) {
        return calendarMapper.insertEventByUserName(calendar);
    }

    public int updateEvent(Calendar calendar) {
        return calendarMapper.updateEvent(calendar);
    }

    public int delectEvent(int id) {

        return calendarMapper.delectEvent(id);
    }

    public int selectUnfinshEvents(String name) {

        int total = calendarMapper.selectAllEvents(name);
        int finished = calendarMapper.selectFinishedEvents(name);
        return total-finished;
    }
}
