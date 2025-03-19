package com.sparta.api.schedule.dto;

import com.sparta.api.schedule.entity.Schedule;
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

    public ScheduleResDto(Schedule schedule) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        this.id = schedule.getId();
        this.schedule = schedule.getSchedule();
        this.regNm = schedule.getRegNm();
        this.regDt = formatter.format(schedule.getRegDt());
        this.modDt = formatter.format(schedule.getModDt());
    }
}
