package hexlet.code.app.controller;

import hexlet.code.app.dto.TasksDTO.TaskCreateDTO;
import hexlet.code.app.dto.TasksDTO.TaskDTO;
import hexlet.code.app.dto.TasksDTO.TaskParamsDTO;
import hexlet.code.app.dto.TasksDTO.TaskUpdateDTO;
import hexlet.code.app.repository.TaskRepository;
import hexlet.code.app.service.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@AllArgsConstructor
public class TasksController {
    private final TaskRepository taskRepository;
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskDTO>> index(TaskParamsDTO params) {
        var result = taskService.findAll(params);
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(result.size()))
                .body(result);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDTO show(@PathVariable Long id) {
        return taskService.findById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO created(@Valid @RequestBody TaskCreateDTO taskData) {
        return taskService.save(taskData);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDTO update(@RequestBody TaskUpdateDTO taskData, @PathVariable Long id) {
        return taskService.updateById(taskData, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        taskService.deleteById(id);
    }
}
