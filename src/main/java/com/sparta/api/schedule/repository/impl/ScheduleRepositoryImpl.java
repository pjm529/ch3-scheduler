package com.sparta.api.schedule.repository.impl;


import com.sparta.api.schedule.dto.ScheduleResDto;
import com.sparta.api.schedule.entity.Schedule;
import com.sparta.api.schedule.repository.ScheduleRepository;
import com.sparta.common.component.CommonExceptionResultMessage;
import com.sparta.common.exception.CustomException;
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
import java.util.stream.Collectors;

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

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("schedule", schedule.getSchedule());
        parameters.put("regNm", schedule.getRegNm());
        parameters.put("password", schedule.getPassword());
        parameters.put("regDt", schedule.getRegDt());
        parameters.put("modDt", schedule.getModDt());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters)); // PK Return
        schedule.setId(key.longValue());

        return new ScheduleResDto(schedule);
    }

    @Override
    public List<ScheduleResDto> findAllSchedule(String modDt, String regNm) {
        List<Object> params = new ArrayList<>();

        StringBuilder query = new StringBuilder()
                .append("SELECT id, schedule, reg_nm, password, reg_dt, mod_dt FROM schedule \n")
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

        List<Schedule> resultList = jdbcTemplate.query(query.toString(), params.toArray(), this.scheduleRowMapper());
        return resultList.stream()
                .map(ScheduleResDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public ScheduleResDto findScheduleById(Long id) {
        StringBuilder query = new StringBuilder()
                .append("SELECT id, schedule, reg_nm, password, reg_dt, mod_dt FROM schedule \n")
                .append(" WHERE id = ? \n");

        List<Schedule> resultList = jdbcTemplate.query(query.toString(), this.scheduleRowMapper(), id);

        return resultList.stream().findAny()
                .map(ScheduleResDto::new)
                .orElseThrow(() -> new CustomException(CommonExceptionResultMessage.NOT_FOUND, "일정 조회 실패: ID " + id + "에 해당하는 일정 없음"));
    }

    private RowMapper<Schedule> scheduleRowMapper() {
        return (rs, rowNum) -> new Schedule(
                rs.getLong("id"),
                rs.getString("schedule"),
                rs.getString("reg_nm"),
                rs.getString("password"),
                rs.getTimestamp("reg_dt").toLocalDateTime(),
                rs.getTimestamp("mod_dt").toLocalDateTime()
        );
    }
}
