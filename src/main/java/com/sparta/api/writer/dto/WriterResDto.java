package com.sparta.api.writer.dto;

import com.sparta.api.writer.entity.Writer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class WriterResDto {

    @Schema(description = "PK")
    private Long id;

    @Schema(description = "이름")
    private String name;

    @Schema(description = "이메일")
    private String email;

    @Schema(description = "등록일")
    private String regDt;

    @Schema(description = "수정일")
    private String modDt;

    public WriterResDto(Writer writer) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        this.id = writer.getId();
        this.name = writer.getName();
        this.email = writer.getEmail();
        this.regDt = formatter.format(writer.getRegDt());
        this.modDt = formatter.format(writer.getModDt());
    }
}
