package com.sparta.api.schedule.service;

import com.sparta.api.schedule.dto.ScheduleDelDto;
import com.sparta.api.schedule.dto.ScheduleModDto;
import com.sparta.api.schedule.dto.ScheduleReqDto;
import com.sparta.api.schedule.dto.ScheduleResDto;

import java.util.List;

public interface ScheduleService {

    ScheduleResDto saveSchedule(ScheduleReqDto dto); // 일정 저장

    List<ScheduleResDto> findAllSchedule(Long writerId, String modDt); // 일정 목록 조회

    ScheduleResDto findScheduleById(Long id); // 일정 상세 조회

    ScheduleResDto updateSchedule(Long id, ScheduleModDto dto); // 일정 저장

    void deleteSchedule(Long id, ScheduleDelDto dto); // 일정 삭제
}
