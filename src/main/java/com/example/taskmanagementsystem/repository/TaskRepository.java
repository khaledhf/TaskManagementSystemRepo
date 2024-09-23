package com.example.taskmanagementsystem.repository;

import com.example.taskmanagementsystem.model.ChangeLog;
import com.example.taskmanagementsystem.model.Task;
import com.example.taskmanagementsystem.model.TaskStatus;
import com.example.taskmanagementsystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import java.time.LocalDateTime;

@Repository
public class TaskRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ChangeLogRepository changeLogRepository;

    @Autowired
    public TaskRepository(JdbcTemplate jdbcTemplate, ChangeLogRepository changeLogRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.changeLogRepository = changeLogRepository;
    }
    public Long createTask(Task task) {
        String sql = "INSERT INTO task (title, status, created_at, updated_at) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, task.getTitle());
            ps.setString(2, task.getStatus().toString());
            ps.setTimestamp(3, Timestamp.valueOf(task.getCreatedAt()));
            ps.setTimestamp(4, Timestamp.valueOf(task.getUpdatedAt()));
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Task getTaskById(Long id) {
        String sql = "SELECT * FROM task WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new TaskRowMapper(), id);
    }

    public int changeTaskStatus(Task task, TaskStatus status) {
        String sql = "UPDATE task SET status = ?, updated_at = NOW() WHERE id = ?";
        ChangeLog changeLog = new ChangeLog();
        changeLog.setTaskId(task.getId());
        changeLog.setPreviousStatus(task.getStatus());
        changeLog.setNewStatus(status);
        changeLog.setUpdatedAt(LocalDateTime.now());
        changeLogRepository.save(changeLog);
        return jdbcTemplate.update(sql, status.toString(), task.getId());
    }
    public List<Task> getAllTasks() {
        String sql = "SELECT * FROM task";
        return jdbcTemplate.query(sql, new TaskRowMapper());
    }
}
