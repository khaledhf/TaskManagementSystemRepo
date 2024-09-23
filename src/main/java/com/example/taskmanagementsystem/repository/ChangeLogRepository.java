package com.example.taskmanagementsystem.repository;

import com.example.taskmanagementsystem.model.ChangeLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class ChangeLogRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ChangeLogRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public void save(ChangeLog changeLog) {
        String sql = "INSERT INTO changelog (task_id, status_from, status_to, changed_at) VALUES (?, ?, ?, ?)";
        System.out.println(changeLog.getTaskId());
        jdbcTemplate.update(sql,
                changeLog.getTaskId(),
                changeLog.getPreviousStatus() != null ? changeLog.getPreviousStatus().toString() : null,
                changeLog.getNewStatus().toString(),
                Timestamp.valueOf(changeLog.getUpdatedAt())
        );
    }

    public List<ChangeLog> findAll() {
        String sql = "SELECT * FROM changelog";
        return jdbcTemplate.query(sql, new ChangeLogRowMapper());
    }

}
