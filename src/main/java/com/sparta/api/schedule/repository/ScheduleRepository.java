package com.sparta.api.schedule.repository;

import com.sparta.api.schedule.dto.ScheduleResDto;
import com.sparta.api.schedule.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {
    ScheduleResDto saveSchedule(Schedule schedule);

    List<ScheduleResDto> findAllSchedule(String modDt, String regNm);

}
