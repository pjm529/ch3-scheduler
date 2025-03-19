package com.sparta.api.schedule.service.impl;

import com.sparta.api.schedule.dto.ScheduleReqDto;
import com.sparta.api.schedule.dto.ScheduleResDto;
import com.sparta.api.schedule.entity.Schedule;
import com.sparta.api.schedule.repository.ScheduleRepository;
import com.sparta.api.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service("scheduleService")
@RequiredArgsConstructor
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Override
    public ScheduleResDto saveSchedule(ScheduleReqDto dto) {
        LocalDateTime now = LocalDateTime.now(); // 현재 시각
        Schedule schedule = new Schedule(dto.getSchedule(), dto.getRegNm(), dto.getPassword(), now, now);
        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResDto> findAllSchedule(String modDt, String regNm) {
        return scheduleRepository.findAllSchedule(modDt, regNm);
    }

    @Override
    public ScheduleResDto findScheduleById(Long id) {
        return scheduleRepository.findScheduleById(id);
    }
}
