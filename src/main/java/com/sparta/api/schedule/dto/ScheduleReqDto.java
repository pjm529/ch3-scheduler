package com.sparta.api.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ScheduleReqDto {

    @Schema(description = "일정 내용")
    @NotBlank
    private String schedule;

    @Schema(description = "작성자 명")
    @NotBlank
    private String regNm;

    @Schema(description = "비밀번호")
    @NotBlank
    private String password;

    @Schema(description = "작성자 이메일")
    @NotBlank @Email
    private String email;
}
