package com.sparta.api.schedule.repository;

import com.sparta.api.schedule.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    Schedule saveSchedule(Schedule schedule);

    List<Schedule> findAllSchedule(Long writerId, String modDt);

    Optional<Schedule> findScheduleById(Long id);

    int updateSchedule(Schedule schedule);

    int deleteSchedule(Schedule schedule);
}
