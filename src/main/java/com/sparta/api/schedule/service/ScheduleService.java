package com.sparta.api.schedule.service;

import com.sparta.api.schedule.dto.*;
import com.sparta.common.component.CustomPageable;

import java.util.List;

public interface ScheduleService {

    ScheduleResDto saveSchedule(ScheduleReqDto dto); // 일정 저장

    List<ScheduleResDto> findAllSchedule(CustomPageable pageable, ScheduleSearchDto dto); // 일정 목록 조회

    ScheduleResDto findScheduleById(Long id); // 일정 상세 조회

    ScheduleResDto updateSchedule(Long id, ScheduleModDto dto); // 일정 저장

    void deleteSchedule(Long id, ScheduleDelDto dto); // 일정 삭제
}
