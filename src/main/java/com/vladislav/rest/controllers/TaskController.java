package com.vladislav.rest.controllers;

import com.vladislav.rest.controllers.requests.PageRequestBody;
import com.vladislav.rest.exceptions.ResourceNotFoundException;
import com.vladislav.rest.models.Employee;
import com.vladislav.rest.models.Task;
import com.vladislav.rest.services.TaskService;
import com.vladislav.rest.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @GetMapping("/tasks")
    public Page<Task> pageTasks(@RequestBody PageRequestBody pageBody) {
        return service.pageTasks(pageBody);
    }

    @GetMapping("/tasks/{uuid}")
    public Task getTaskByUUID(@PathVariable UUID uuid) {
        return service.getByUUID(uuid);
    }

    @PostMapping("/tasks")
    public Task createTask(@RequestBody Task Employee) {
        return service.save(Employee);
    }

    @PutMapping("/tasks/{uuid}")
    public Task putTask(@RequestBody Task incomingTask, @PathVariable UUID uuid) {
        try {
            final Task task = service.getByUUID(uuid);
            BeanUtils.copyPropertiesExcludeNullProperties(incomingTask, task);
            return service.save(task);
        } catch (ResourceNotFoundException ignore) {
            return service.save(incomingTask);
        }
    }

    @DeleteMapping("/tasks/{uuid}")
    public void deleteTask(@PathVariable UUID uuid) {
        service.delete(uuid);
    }

    @GetMapping("/tasks/{uuid}/employees")
    public List<Employee> getTaskEmployees(@PathVariable UUID uuid) {
        return service.getAllTaskEmployees(uuid);
    }

}
