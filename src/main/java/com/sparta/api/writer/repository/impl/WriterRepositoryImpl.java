package com.sparta.api.writer.repository.impl;

import com.sparta.api.schedule.entity.Schedule;
import com.sparta.api.writer.entity.Writer;
import com.sparta.api.writer.repository.WriterRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @Override
    public Optional<Writer> findWriterByEmail(String email) {
        StringBuilder query = new StringBuilder()
                .append("SELECT id, name, email, password, reg_dt, mod_dt FROM writer \n")
                .append(" WHERE email = ? \n");

        List<Writer> resultList = jdbcTemplate.query(query.toString(), this.writerRowMapper(), email);
        return resultList.stream().findAny();
    }

    private RowMapper<Writer> writerRowMapper() {
        return (rs, rowNum) -> new Writer(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getTimestamp("reg_dt").toLocalDateTime(),
                rs.getTimestamp("mod_dt").toLocalDateTime()
        );
    }
}
