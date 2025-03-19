package com.sparta.api.writer.repository.impl;

import com.sparta.api.writer.entity.Writer;
import com.sparta.api.writer.repository.WriterRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository("writerRepository")
public class WriterRepositoryImpl implements WriterRepository {

    private final JdbcTemplate jdbcTemplate;

    public WriterRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Writer saveWriter(Writer writer) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("writer").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", writer.getName());
        parameters.put("email", writer.getEmail());
        parameters.put("password", writer.getPassword());
        parameters.put("regDt", writer.getRegDt());
        parameters.put("modDt", writer.getModDt());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters)); // PK Return
        writer.setId(key.longValue());

        return writer;
    }
}
