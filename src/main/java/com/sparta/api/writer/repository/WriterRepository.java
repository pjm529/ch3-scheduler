package com.sparta.api.writer.repository;

import com.sparta.api.writer.entity.Writer;

import java.util.Optional;

public interface WriterRepository {

    Writer saveWriter(Writer writer);

    Optional<Writer> findWriterByEmail(String email);

    Optional<Writer> findWriterById(Long id);

    int updateWriter(Writer writer);

}
