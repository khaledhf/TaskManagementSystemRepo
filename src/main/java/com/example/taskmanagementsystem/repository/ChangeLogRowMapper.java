package com.example.taskmanagementsystem.repository;

import com.example.taskmanagementsystem.model.ChangeLog;
import com.example.taskmanagementsystem.model.TaskStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangeLogRowMapper implements RowMapper<ChangeLog> {

    @Override
    public ChangeLog mapRow(ResultSet rs, int rowNum) throws SQLException {
        ChangeLog changeLog = new ChangeLog();
        changeLog.setId(rs.getLong("id"));
        changeLog.setTaskId(rs.getLong("task_id"));
        changeLog.setPreviousStatus(TaskStatus.valueOf(rs.getString("status_from")));
        changeLog.setNewStatus(TaskStatus.valueOf(rs.getString("status_to")));
        changeLog.setUpdatedAt(rs.getTimestamp("changed_at").toLocalDateTime());
        return changeLog;
    }
}
