package com.sparta.api.writer.service.impl;

import com.sparta.api.writer.dto.WriterModDto;
import com.sparta.api.writer.dto.WriterReqDto;
import com.sparta.api.writer.dto.WriterResDto;
import com.sparta.api.writer.entity.Writer;
import com.sparta.api.writer.repository.WriterRepository;
import com.sparta.api.writer.service.WriterService;
import com.sparta.common.component.CommonExceptionResultMessage;
import com.sparta.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service("writerService")
@RequiredArgsConstructor
@Transactional
public class WriterServiceImpl implements WriterService {

    private final WriterRepository writerRepository;

    @Override
    public WriterResDto saveWriter(WriterReqDto dto) {
        Optional<Writer> writerOpt = writerRepository.findWriterByEmail(dto.getEmail());

        // 해당 이메일로 가입된 회원이 있을 경우
        if (writerOpt.isPresent()) {
            throw new CustomException(CommonExceptionResultMessage.DUPLICATE_FAIL, "이미 사용 중인 이메일입니다.");
        }

        LocalDateTime now = LocalDateTime.now(); // 현재 시각

        Writer writer = new Writer(dto.getName(), dto.getEmail(), now, now);
        return new WriterResDto(writerRepository.saveWriter(writer));
    }

    @Override
    public WriterResDto findWriterById(Long id) {
        Writer writer = writerRepository.findWriterById(id)
                .orElseThrow(() -> new CustomException(CommonExceptionResultMessage.NOT_FOUND, "회원 조회 실패: id: " + id + " 에 해당하는 회원 없음"));

        return new WriterResDto(writer);
    }

    @Override
    public WriterResDto updateWriter(Long id, WriterModDto dto) {
        Writer writer = writerRepository.findWriterById(id)
                .orElseThrow(() -> new CustomException(CommonExceptionResultMessage.NOT_FOUND, "회원 조회 실패: id: " + id + " 에 해당하는 회원 없음"));

        writer.setName(dto.getName());
        writer.setModDt(LocalDateTime.now());

        int result = writerRepository.updateWriter(writer);
        if (result == 0) { // update 된 row 가 없으면 throw
            throw new CustomException(CommonExceptionResultMessage.DB_FAIL);
        }

        return new WriterResDto(writer);
    }
}
