package com.example.taskmanagementsystem.controller;

import com.example.taskmanagementsystem.model.ChangeLog;
import com.example.taskmanagementsystem.model.Task;
import com.example.taskmanagementsystem.model.TaskStatus;
import com.example.taskmanagementsystem.repository.ChangeLogRepository;
import com.example.taskmanagementsystem.repository.TaskRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class TaskController {
    private final TaskRepository taskRepository;
    private final ChangeLogRepository changeLogRepository;

    public TaskController(TaskRepository taskRepository, ChangeLogRepository changeLogRepository) {
        this.taskRepository = taskRepository;
        this.changeLogRepository = changeLogRepository;
    }
    @GetMapping("/tasks")
    public String taskDashboard(ModelMap modelMap) {
        modelMap.addAttribute("task", new Task());
        return "welcome";
    }

    @GetMapping("/tasks/new")
    public String showNewTaskForm() {
        return "newTask";
    }
    @PostMapping("/tasks/new")
    public String createTask(@RequestParam String taskName) {
        Task task = new Task();
        task.setTitle(taskName);
        task.setStatus(TaskStatus.PENDING);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());


        Long taskId = taskRepository.createTask(task);

        ChangeLog changeLog = new ChangeLog();
        changeLog.setTaskId(taskId);
        System.out.println(taskId);
        changeLog.setPreviousStatus(task.getStatus());
        changeLog.setNewStatus(task.getStatus());
        changeLog.setUpdatedAt(LocalDateTime.now());

        changeLogRepository.save(changeLog);

        return "welcome";
    }


    @GetMapping("/tasks/status")
    public String showStatusForm() {
        return "taskStatusChange";
    }
    @PostMapping("/tasks/status")
    public String showChangeStatusForm(@RequestParam("status") String status, @RequestParam("taskId") Long id) {
        System.out.println(id);
        TaskStatus newStatus = TaskStatus.valueOf(status);
        taskRepository.changeTaskStatus(taskRepository.getTaskById(id), newStatus);
        return "welcome";
    }
    @GetMapping("/log")
    public String showTaskLog(ModelMap model) {
        List<ChangeLog> taskLogs = changeLogRepository.findAll();
        System.out.println(taskLogs.size());
        model.addAttribute("taskLogs", taskLogs);
        return "tasksLog";
    }
}