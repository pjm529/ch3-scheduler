package com.sparta.common.component;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CustomPageable {
    @Schema(description = "페이지 번호 (1부터 시작)", defaultValue = "1")
    private int page = 1;

    @Schema(description = "페이지 크기", defaultValue = "10")
    private int size = 10;

    public int getOffset() {
        return (this.page - 1) * this.size;
    }
}
