package com.sparta.api.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ScheduleReqDto {

    @Schema(description = "일정 내용")
    @NotBlank(message = "일정 내용을 입력해주세요.")
    private String schedule;

    @Schema(description = "비밀번호")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @Schema(description = "작성자 이메일")
    @NotBlank(message = "작성자 이메일을 입력해주세요.")
    @Email(message = "유효하지 않는 이메일입니다.")
    private String email;
}
