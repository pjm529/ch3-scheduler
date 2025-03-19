package com.sparta.api.test.controller;

import com.sparta.common.annotation.ApiErrorCodeExamples;
import com.sparta.common.component.BaseResponse;
import com.sparta.common.component.CommonExceptionResultMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
@CrossOrigin("*")
@Tag(name = "Test API", description = "Test 관련 API 모음.")
public class TestController {

    @GetMapping
    @Operation(
            summary = "Test",
            description = "Test",
            parameters = @Parameter(name = "test", description = "테스트 Param")
    )
    @ApiErrorCodeExamples({CommonExceptionResultMessage.FAIL
            , CommonExceptionResultMessage.DB_FAIL
    })
    public BaseResponse<Boolean> test(@RequestParam String test) {
        return BaseResponse.from(true);

    }
}
