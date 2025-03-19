package com.sparta.api.writer.controller;

import com.sparta.api.writer.dto.WriterReqDto;
import com.sparta.api.writer.dto.WriterResDto;
import com.sparta.api.writer.service.WriterService;
import com.sparta.common.annotation.ApiErrorCodeExamples;
import com.sparta.common.component.BaseResponse;
import com.sparta.common.component.CommonExceptionResultMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/writer")
@CrossOrigin("*")
@Tag(name = "Writer API", description = "writer 관련 API 모음.")
public class WriterController {

    private final WriterService writerService;

    @PostMapping
    @Operation(
            summary = "작성자 회원가입 API",
            description = "작성자 회원가입하기 위한 API"
    )
    @ApiErrorCodeExamples({CommonExceptionResultMessage.VALID_FAIL
            , CommonExceptionResultMessage.DB_FAIL
            , CommonExceptionResultMessage.FAIL
    })
    public BaseResponse<WriterResDto> saveWriter(@RequestBody @Valid WriterReqDto dto) {
        return BaseResponse.from(writerService.saveWriter(dto));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "작성자 상세 조회 API",
            description = "작성자 상세 조회 API"
    )
    @ApiErrorCodeExamples({CommonExceptionResultMessage.VALID_FAIL
            , CommonExceptionResultMessage.PW_MISMATCH
            , CommonExceptionResultMessage.NOT_FOUND
            , CommonExceptionResultMessage.DB_FAIL
            , CommonExceptionResultMessage.FAIL
    })
    public BaseResponse<WriterResDto> findWriterById(@Schema(description = "작성자 PK") @PathVariable Long id) {
        return BaseResponse.from(writerService.findWriterById(id));
    }
}
