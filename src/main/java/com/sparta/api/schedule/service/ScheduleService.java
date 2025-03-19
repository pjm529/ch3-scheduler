package com.sparta.api.schedule.service;

import com.sparta.api.schedule.dto.ScheduleReqDto;
import com.sparta.api.schedule.dto.ScheduleResDto;

import java.util.List;

public interface ScheduleService {

    ScheduleResDto saveSchedule(ScheduleReqDto dto); // 일정 저장

    List<ScheduleResDto> findAllSchedule(String modDt, String regNm); // 일정 목록 조회

    ScheduleResDto findScheduleById(Long id); // 일정 상세 조회

    ScheduleResDto updateSchedule(Long id, ScheduleReqDto dto); // 일정 저장

}
