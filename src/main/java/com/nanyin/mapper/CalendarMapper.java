package com.nanyin.mapper;

import com.nanyin.entity.Calendar;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by NanYin on 2017-08-15 下午2:24.
 * 包名： com.nanyin.mapper
 * 类描述：
 */
@Repository
public interface CalendarMapper {

    List<Calendar> findEventByUserName(String name);

    int insertEventByUserName(Calendar calendar);

    int updateEvent(Calendar calendar);

    int delectEvent(int id);

    int selectAllEvents(String name);

    int selectFinishedEvents(String name);
}
