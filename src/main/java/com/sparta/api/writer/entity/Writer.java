package com.sparta.api.writer.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Writer {

    private Long id; // PK

    private String name; // 이름

    private String email; // 이메일

    private LocalDateTime regDt; // 등록일

    private LocalDateTime modDt; // 수정일

    public Writer(String name, String email, LocalDateTime regDt, LocalDateTime modDt) {
        this.name = name;
        this.email = email;
        this.regDt = regDt;
        this.modDt = modDt;
    }

    public Writer(Long id, String name, String email, LocalDateTime regDt, LocalDateTime modDt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.regDt = regDt;
        this.modDt = modDt;
    }
}
