package com.sparta.api.writer.service;

import com.sparta.api.writer.dto.WriterReqDto;
import com.sparta.api.writer.dto.WriterResDto;

public interface WriterService {
    WriterResDto saveWriter(WriterReqDto dto);
}
