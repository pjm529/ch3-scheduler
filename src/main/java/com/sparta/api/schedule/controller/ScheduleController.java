package com.sparta.api.schedule.controller;

import com.sparta.api.schedule.dto.ScheduleDelDto;
import com.sparta.api.schedule.dto.ScheduleReqDto;
import com.sparta.api.schedule.dto.ScheduleResDto;
import com.sparta.api.schedule.service.ScheduleService;
import com.sparta.common.annotation.ApiErrorCodeExamples;
import com.sparta.common.component.BaseResponse;
import com.sparta.common.component.CommonExceptionResultMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            description = "일정 등록하기 위한 API"
    )
    @ApiErrorCodeExamples({CommonExceptionResultMessage.VALID_FAIL
            , CommonExceptionResultMessage.DB_FAIL
            , CommonExceptionResultMessage.FAIL
    })
    public BaseResponse<ScheduleResDto> saveSchedule(@RequestBody @Valid ScheduleReqDto dto) {
        return BaseResponse.from(scheduleService.saveSchedule(dto));
    }

    @GetMapping
    @Operation(
            summary = "일정 목록 조회 API",
            description = "일정 목록을 조회하기 위한 API",
            parameters = {
                    @Parameter(name = "modDt", description = "수정일")
                    , @Parameter(name = "regNm", description = "등록자명")
            }
    )
    @ApiErrorCodeExamples({CommonExceptionResultMessage.NOT_FOUND
            , CommonExceptionResultMessage.DB_FAIL
            , CommonExceptionResultMessage.FAIL
    })
    public BaseResponse<List<ScheduleResDto>> findAllSchedule(
            @RequestParam(required = false) String modDt
            , @RequestParam(required = false) String regNm) {
        return BaseResponse.from(scheduleService.findAllSchedule(modDt, regNm));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "일정 상세 조회 API",
            description = "일정 상세를 조회하기 위한 API"
    )
    @ApiErrorCodeExamples({CommonExceptionResultMessage.NOT_FOUND
            , CommonExceptionResultMessage.DB_FAIL
            , CommonExceptionResultMessage.FAIL
    })
    public BaseResponse<ScheduleResDto> findScheduleById(@Schema(description = "일정 PK") @PathVariable Long id) {
        return BaseResponse.from(scheduleService.findScheduleById(id));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "일정 수정 API",
            description = "일정 수정하기 위한 API"
    )
    @ApiErrorCodeExamples({CommonExceptionResultMessage.VALID_FAIL
            , CommonExceptionResultMessage.PW_MISMATCH
            , CommonExceptionResultMessage.NOT_FOUND
            , CommonExceptionResultMessage.DB_FAIL
            , CommonExceptionResultMessage.FAIL
    })
    public BaseResponse<ScheduleResDto> updateSchedule(
            @Schema(description = "일정 PK") @PathVariable Long id,
            @RequestBody @Valid ScheduleReqDto dto) {
        return BaseResponse.from(scheduleService.updateSchedule(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "일정 삭제 API",
            description = "일정 삭제하기 위한 API"
    )
    @ApiErrorCodeExamples({CommonExceptionResultMessage.VALID_FAIL
            , CommonExceptionResultMessage.PW_MISMATCH
            , CommonExceptionResultMessage.NOT_FOUND
            , CommonExceptionResultMessage.DB_FAIL
            , CommonExceptionResultMessage.FAIL
    })
    public BaseResponse<Boolean> deleteSchedule(
            @Schema(description = "일정 PK") @PathVariable Long id,
            @RequestBody @Valid ScheduleDelDto dto) {
        scheduleService.deleteSchedule(id, dto);
        return BaseResponse.from(true);
    }
}
