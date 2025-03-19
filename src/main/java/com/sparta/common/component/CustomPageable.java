package com.sparta.common.component;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class CustomPageable {
    @Schema(description = "페이지 번호 (1부터 시작)", defaultValue = "1")
    @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다.")
    private int page = 1;

    @Schema(description = "한 페이지에 표시할 데이터 개수", defaultValue = "10")
    @Min(value = 1, message = "표시 데이터 개수는 1 이상이어야 합니다.")
    private int size = 10;

    public int getOffset() {
        return (this.page - 1) * this.size;
    }
}
