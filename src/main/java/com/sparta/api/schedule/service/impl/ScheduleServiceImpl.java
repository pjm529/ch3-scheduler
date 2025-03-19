package com.sparta.api.schedule.service.impl;

import com.sparta.api.schedule.dto.ScheduleDelDto;
import com.sparta.api.schedule.dto.ScheduleReqDto;
import com.sparta.api.schedule.dto.ScheduleResDto;
import com.sparta.api.schedule.entity.Schedule;
import com.sparta.api.schedule.repository.ScheduleRepository;
import com.sparta.api.schedule.service.ScheduleService;
import com.sparta.api.writer.dto.WriterResDto;
import com.sparta.api.writer.entity.Writer;
import com.sparta.api.writer.repository.WriterRepository;
import com.sparta.api.writer.service.WriterService;
import com.sparta.common.component.CommonExceptionResultMessage;
import com.sparta.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service("scheduleService")
@RequiredArgsConstructor
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final WriterRepository writerRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public ScheduleResDto saveSchedule(ScheduleReqDto dto) {
        Writer writer = writerRepository.findWriterByEmail(dto.getEmail())
                .orElseThrow(() -> new CustomException(CommonExceptionResultMessage.NOT_FOUND, "회원 조회 실패: email: " + dto.getEmail() + " 에 해당하는 회원 없음"));

        LocalDateTime now = LocalDateTime.now(); // 현재 시각
        String encodePw = passwordEncoder.encode(dto.getPassword()); // 비밀번호 암호화

        Schedule schedule = new Schedule(dto.getSchedule(), encodePw, now, now, writer);
        return new ScheduleResDto(scheduleRepository.saveSchedule(schedule));
    }

    @Override
    public List<ScheduleResDto> findAllSchedule(String modDt, String regNm) {
        return scheduleRepository.findAllSchedule(modDt, regNm).stream()
                .map(ScheduleResDto::new) // 일정 목록 조회 후  mapping
                .collect(Collectors.toList());
    }

    @Override
    public ScheduleResDto findScheduleById(Long id) {
        return scheduleRepository.findScheduleById(id)
                .map(ScheduleResDto::new) // 일정 조회 후 mapping
                .orElseThrow(() -> new CustomException(CommonExceptionResultMessage.NOT_FOUND, "일정 조회 실패: ID " + id + " 에 해당하는 일정 없음"));
    }

    @Override
    public ScheduleResDto updateSchedule(Long id, ScheduleReqDto dto) {
        // 유효한 일정인지 조회
        Schedule schedule = this.validSchedule(id, dto.getPassword());

        // 새로운 정보 update
        schedule.setSchedule(dto.getSchedule()); // 일정
        schedule.setModDt(LocalDateTime.now()); // 수정 시간

        // 일정 수정
        int result = scheduleRepository.updateSchedule(schedule);
        if (result == 0) { // update 된 row 가 없으면 throw
            throw new CustomException(CommonExceptionResultMessage.DB_FAIL);
        }

        return new ScheduleResDto(schedule);
    }

    @Override
    public void deleteSchedule(Long id, ScheduleDelDto dto) {
        // 유효한 일정인지 조회
        Schedule schedule = this.validSchedule(id, dto.getPassword());

        schedule.setDelDt(LocalDateTime.now()); // 삭제 시간

        int result = scheduleRepository.deleteSchedule(schedule); // 일정 삭제
        if (result == 0) { // update 된 row 가 없으면 throw
            throw new CustomException(CommonExceptionResultMessage.DB_FAIL);
        }
    }

    private Schedule validSchedule(Long id, String inputPw) {
        // 유효한 일정인지 조회
        Schedule schedule = scheduleRepository.findScheduleById(id)
                .orElseThrow(() -> new CustomException(CommonExceptionResultMessage.NOT_FOUND, "일정 조회 실패: ID " + id + " 에 해당하는 일정 없음"));

        String pw = schedule.getPassword();

        // 비밀번호 검사
        if (!passwordEncoder.matches(inputPw, pw)) {
            throw new CustomException(CommonExceptionResultMessage.PW_MISMATCH);
        }

        return schedule;
    }

}
