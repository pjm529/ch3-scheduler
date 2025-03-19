package com.sparta.common.component;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaginationResDto<T> {

    @Schema(description = "결과 Data 목록")
    private List<T> data;

    @Schema(description = "총 Data 수")
    private Long total;

    @Schema(description = "한 페이지에 표시할 데이터 개수")
    private Integer size;

    @Schema(description = "페이지 번호")
    private Integer page;

    @Schema(description = "총 페이지 수")
    private Long totalPages;
}
