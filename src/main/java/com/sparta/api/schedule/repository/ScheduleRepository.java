package com.sparta.api.schedule.repository;

import com.sparta.api.schedule.dto.ScheduleSearchDto;
import com.sparta.api.schedule.entity.Schedule;
import com.sparta.common.component.CustomPageable;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    Schedule saveSchedule(Schedule schedule);

    List<Schedule> findAllSchedule(CustomPageable pageable, ScheduleSearchDto dto);

    Optional<Schedule> findScheduleById(Long id);

    int updateSchedule(Schedule schedule);

    int deleteSchedule(Schedule schedule);
}
