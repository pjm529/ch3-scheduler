package com.sparta.api.schedule.repository.impl;


import com.sparta.api.schedule.dto.ScheduleResDto;
import com.sparta.api.schedule.entity.Schedule;
import com.sparta.api.schedule.repository.ScheduleRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public List<ScheduleResDto> findAllSchedule(String modDt, String regNm) {
        List<Object> params = new ArrayList<>();

        StringBuilder query = new StringBuilder()
                .append("SELECT id, schedule, reg_nm, reg_dt, mod_dt FROM schedule \n")
                .append(" WHERE 1 = 1 \n");

        if (StringUtils.isNotBlank(modDt)) { // 수정일 검색조건이 있을 경우
            query.append(" AND DATE(mod_dt) = ? \n");
            params.add(modDt);
        }

        if (StringUtils.isNotBlank(regNm)) { // 작성자명 검색조건이 있을 경우
            query.append(" AND reg_nm = ? \n");
            params.add(regNm);
        }

        query.append(" ORDER BY mod_dt DESC");
        return jdbcTemplate.query(query.toString(), params.toArray(), this.scheduleRowMapper());
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
