package com.sparta.api.schedule.repository;

import com.sparta.api.schedule.dto.ScheduleResDto;
import com.sparta.api.schedule.entity.Schedule;

public interface ScheduleRepository {
    ScheduleResDto saveSchedule(Schedule schedule);
}
