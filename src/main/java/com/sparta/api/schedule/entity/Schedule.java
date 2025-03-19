package com.sparta.api.schedule.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Schedule {

    private Long id; // PK

    private String schedule; // 일정 내용

    private String regNm; // 작성자 명

    private String password; // 비밀번호

    private LocalDateTime regDt; // 작성일

    private LocalDateTime modDt; // 수정일
}
