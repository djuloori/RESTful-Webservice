package com.github.djuloori.whiteboard.service;

import com.github.djuloori.whiteboard.dao.ScheduleDao;
import com.github.djuloori.whiteboard.rest.ScheduleRO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduleService {

    @Autowired
    private ScheduleDao scheduleDao;

    @Autowired
    private ScheduleRO scheduleRO;

    public String syncSchedule(ScheduleRO scheduleRO){
        return scheduleDao.addSchedule(scheduleRO);
    }

    public String modifySchedule(ScheduleRO scheduleRo){
        return scheduleDao.editSchedule(scheduleRo);
    }

    public String removeSchedule(String scheduleid){
        scheduleRO.setScheduleId(scheduleid);
        return scheduleDao.removeSchedule(scheduleRO);
    }

}
