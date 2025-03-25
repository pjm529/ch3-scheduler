package com.sparta.api.writer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WriterModDto {

    @Schema(description = "이름")
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
}
