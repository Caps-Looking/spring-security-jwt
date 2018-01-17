package com.example.demo.controllers;

import com.example.demo.models.entities.Task;
import com.example.demo.models.repositories.TaskRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1")
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    @PostMapping(path = "tasks")
    public void addTask(@RequestBody Task task) {
        taskRepository.save(task);
    }

    @ApiOperation(value = "Return a list with all tasks", response = Task[].class)
    @GetMapping(path = "tasks")
    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    @PutMapping(path = "tasks/{id}")
    public void editTask(@PathVariable long id, @RequestBody Task task) {
        Task existingTask = taskRepository.findOne(id);
        Assert.notNull(existingTask, "Task not found");
        existingTask.setDescription(task.getDescription());
        taskRepository.save(existingTask);
    }

    @DeleteMapping(path = "tasks/{id}")
    public void deleteTask(@PathVariable long id) {
        taskRepository.delete(id);
    }

}
