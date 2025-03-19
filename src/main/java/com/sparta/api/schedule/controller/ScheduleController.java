package com.sparta.api.schedule.controller;

import com.sparta.api.schedule.dto.ScheduleReqDto;
import com.sparta.api.schedule.dto.ScheduleResDto;
import com.sparta.api.schedule.service.ScheduleService;
import com.sparta.common.annotation.ApiErrorCodeExamples;
import com.sparta.common.component.BaseResponse;
import com.sparta.common.component.CommonExceptionResultMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
@CrossOrigin("*")
@Tag(name = "Schedule API", description = "Schedule 관련 API 모음.")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    @Operation(
            summary = "일정 등록 API",
            description = "이메일을 등록하기 위한 API"
    )
    @ApiErrorCodeExamples({CommonExceptionResultMessage.VALID_FAIL
            , CommonExceptionResultMessage.DB_FAIL
    })
    public BaseResponse<ScheduleResDto> saveSchedule(@RequestBody @Valid ScheduleReqDto dto) {
        return BaseResponse.from(scheduleService.saveSchedule(dto));

    }
}
