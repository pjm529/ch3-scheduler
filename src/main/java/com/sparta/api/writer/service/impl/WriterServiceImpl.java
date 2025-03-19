package com.sparta.api.writer.service.impl;

import com.sparta.api.writer.dto.WriterReqDto;
import com.sparta.api.writer.dto.WriterResDto;
import com.sparta.api.writer.entity.Writer;
import com.sparta.api.writer.repository.WriterRepository;
import com.sparta.api.writer.service.WriterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service("writerService")
@RequiredArgsConstructor
@Transactional
public class WriterServiceImpl implements WriterService {

    private final WriterRepository writerRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public WriterResDto saveWriter(WriterReqDto dto) {
        LocalDateTime now = LocalDateTime.now(); // 현재 시각
        String encodePw = passwordEncoder.encode(dto.getPassword()); // 비밀번호 암호화

        Writer writer = new Writer(dto.getName(), dto.getEmail(), encodePw, now, now);
        return new WriterResDto(writerRepository.saveWriter(writer));
    }
}
