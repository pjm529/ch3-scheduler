package com.sparta.api.schedule.entity;

import com.sparta.api.writer.entity.Writer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Schedule {

    private Long id; // PK

    private String schedule; // 일정 내용

    private String password; // 비밀번호

    private LocalDateTime regDt; // 작성일

    private LocalDateTime modDt; // 수정일

    private LocalDateTime delDt; // 삭제일

    private Writer writer; // 작성자 정보

    public Schedule(String schedule, String password, LocalDateTime regDt, LocalDateTime modDt, Writer writer) {
        this.schedule = schedule;
        this.password = password;
        this.regDt = regDt;
        this.modDt = modDt;
        this.writer = writer;
    }

    public Schedule(Long id, String schedule, String password, LocalDateTime regDt, LocalDateTime modDt) {
        this.id = id;
        this.schedule = schedule;
        this.password = password;
        this.regDt = regDt;
        this.modDt = modDt;
    }
}
