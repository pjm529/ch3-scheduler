package com.sparta.api.writer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WriterReqDto {

    @Schema(description = "이름")
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @Schema(description = "이메일")
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "유효하지 않는 이메일입니다.")
    private String email;
}
