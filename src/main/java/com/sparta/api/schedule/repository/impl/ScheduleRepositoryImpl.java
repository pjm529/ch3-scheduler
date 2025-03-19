package com.sparta.api.schedule.repository.impl;


import com.sparta.api.schedule.entity.Schedule;
import com.sparta.api.schedule.repository.ScheduleRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository("scheduleRepository")
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Schedule saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("schedule", schedule.getSchedule());
        parameters.put("password", schedule.getPassword());
        parameters.put("regDt", schedule.getRegDt());
        parameters.put("modDt", schedule.getModDt());
        parameters.put("writer_id", schedule.getWriter().getId()); // 작성자 PK 저장

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters)); // PK Return
        schedule.setId(key.longValue());

        return schedule;
    }

    @Override
    public List<Schedule> findAllSchedule(Long writerId, String modDt) {
        List<Object> params = new ArrayList<>();

        StringBuilder query = new StringBuilder()
                .append("SELECT a.id, a.schedule, a.password, a.reg_dt, a.mod_dt\n")
                .append(" ,b.id AS writer_id, b.email, b.name, b.reg_dt as writer_reg_dt, b.mod_dt as writer_mod_dt\n")
                .append(" FROM schedule a JOIN writer b ON a.writer_id = b.id\n")
                .append(" WHERE a.del_dt IS NULL \n");

        if (writerId != null) { // 작성자 PK 검색조건이 있을 경우
            query.append(" AND b.id = ? \n");
            params.add(writerId);
        }

        if (StringUtils.isNotBlank(modDt)) { // 수정일 검색조건이 있을 경우
            query.append(" AND DATE(a.mod_dt) = ? \n");
            params.add(modDt);
        }

        query.append(" ORDER BY mod_dt DESC");

        return jdbcTemplate.query(query.toString(), params.toArray(), this.scheduleRowMapper());
    }

    @Override
    public Optional<Schedule> findScheduleById(Long id) {
        StringBuilder query = new StringBuilder()
                .append("SELECT a.id, a.schedule, a.password, a.reg_dt, a.mod_dt\n")
                .append(" ,b.id AS writer_id, b.email, b.name, b.reg_dt as writer_reg_dt, b.mod_dt as writer_mod_dt\n")
                .append(" FROM schedule a JOIN writer b ON a.writer_id = b.id\n")
                .append(" WHERE a.id = ? AND a.del_dt IS NULL \n");

        List<Schedule> resultList = jdbcTemplate.query(query.toString(), this.scheduleRowMapper(), id);
        return resultList.stream().findAny();
    }

    @Override
    public int updateSchedule(Schedule schedule) {
        StringBuilder query = new StringBuilder()
                .append("UPDATE schedule \n")
                .append(" SET schedule = ? \n")
                .append("   , mod_dt = ? \n")
                .append(" WHERE id = ? AND del_dt IS NULL ");

        List<Object> params = new ArrayList<>();
        params.add(schedule.getSchedule());
        params.add(schedule.getModDt());
        params.add(schedule.getId());

        return jdbcTemplate.update(query.toString(), params.toArray()); // update row 수 return
    }

    @Override
    public int deleteSchedule(Schedule schedule) {
        StringBuilder query = new StringBuilder()
                .append("UPDATE schedule \n")
                .append(" SET del_dt = ? \n")
                .append(" WHERE id = ? AND del_dt IS NULL");

        List<Object> params = new ArrayList<>();
        params.add(schedule.getDelDt());
        params.add(schedule.getId());

        return jdbcTemplate.update(query.toString(), params.toArray()); // delete row 수 return
    }

    private RowMapper<Schedule> scheduleRowMapper() {
        return (rs, rowNum) -> new Schedule(
                rs.getLong("id"),
                rs.getString("schedule"),
                rs.getString("password"),
                rs.getTimestamp("reg_dt").toLocalDateTime(),
                rs.getTimestamp("mod_dt").toLocalDateTime(),
                rs.getLong("writer_id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getTimestamp("writer_reg_dt").toLocalDateTime(),
                rs.getTimestamp("writer_mod_dt").toLocalDateTime()
        );
    }
}
