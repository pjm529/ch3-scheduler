package com.sparta.api.schedule.service;

import com.sparta.api.schedule.dto.ScheduleReqDto;
import com.sparta.api.schedule.dto.ScheduleResDto;

public interface ScheduleService {

    ScheduleResDto saveSchedule(ScheduleReqDto dto);
}
