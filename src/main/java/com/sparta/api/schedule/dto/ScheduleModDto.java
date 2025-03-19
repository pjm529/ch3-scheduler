package com.sparta.api.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ScheduleModDto {

    @Schema(description = "일정 내용")
    @NotBlank
    private String schedule;

    @Schema(description = "비밀번호")
    @NotBlank
    private String password;
}
