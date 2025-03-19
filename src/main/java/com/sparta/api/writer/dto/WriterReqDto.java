package com.sparta.api.writer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WriterReqDto {

    @Schema(description = "이름")
    @NotBlank
    private String name;

    @Schema(description = "이메일")
    @NotBlank @Email
    private String email;

    @Schema(description = "비밀번호")
    @NotBlank
    private String password;
}
