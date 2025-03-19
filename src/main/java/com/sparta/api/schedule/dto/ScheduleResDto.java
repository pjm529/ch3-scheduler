package com.sparta.api.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class ScheduleResDto {

    @Schema(description = "PK")
    private Long id;

    @Schema(description = "일정 내용")
    private String schedule;

    @Schema(description = "작성자 명")
    private String regNm;

    @Schema(description = "등록일")
    private String regDt;

    @Schema(description = "수정일")
    private String modDt;

    public ScheduleResDto(Long id, String schedule, String regNm, LocalDateTime regDt, LocalDateTime modDt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        this.id = id;
        this.schedule = schedule;
        this.regNm = regNm;
        this.regDt = formatter.format(regDt);
        this.modDt = formatter.format(modDt);
    }
}
