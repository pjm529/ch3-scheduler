package com.sparta.api.schedule.repository.impl;


import com.sparta.api.schedule.dto.ScheduleResDto;
import com.sparta.api.schedule.entity.Schedule;
import com.sparta.api.schedule.repository.ScheduleRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("scheduleRepository")
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResDto saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");

        LocalDateTime now = LocalDateTime.now();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("schedule", schedule.getSchedule());
        parameters.put("regNm", schedule.getRegNm());
        parameters.put("password", schedule.getPassword());
        parameters.put("regDt", now);
        parameters.put("modDt", now);

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters)); // PK Return

        return new ScheduleResDto(key.longValue(), schedule.getSchedule(), schedule.getRegNm(), now, now);
    }

    @Override
    public List<ScheduleResDto> findAllSchedule() {
        return jdbcTemplate.query("select * from schedule order by mod_dt desc", this.scheduleRowMapper());
    }

    private RowMapper<ScheduleResDto> scheduleRowMapper() {
        return (rs, rowNum) -> new ScheduleResDto(
                rs.getLong("id"),
                rs.getString("schedule"),
                rs.getString("reg_nm"),
                rs.getTimestamp("reg_dt").toLocalDateTime(),
                rs.getTimestamp("mod_dt").toLocalDateTime()
        );
    }
}
