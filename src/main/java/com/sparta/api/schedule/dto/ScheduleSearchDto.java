package com.sparta.api.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
public class ScheduleSearchDto {

    @Schema(description = "작성자 PK")
    private Long writerId;

    @Schema(description = "수정일")
    private String modDt;
}
