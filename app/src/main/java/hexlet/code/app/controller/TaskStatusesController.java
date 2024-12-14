package hexlet.code.app.controller;

import hexlet.code.app.dto.TaskStatusesDTO.TaskStatusCreateDTO;
import hexlet.code.app.dto.TaskStatusesDTO.TaskStatusDTO;
import hexlet.code.app.dto.TaskStatusesDTO.TaskStatusUpdateDTO;
import hexlet.code.app.service.TaskStatusesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/task_statuses")
@RequiredArgsConstructor
public class TaskStatusesController {
    private final TaskStatusesService taskStatusesService;

    @GetMapping()
    public ResponseEntity<List<TaskStatusDTO>> index() {
        var result = taskStatusesService.findAll();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(result.size()))
                .body(result);
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskStatusDTO create(@Valid @RequestBody TaskStatusCreateDTO taskStatusData) {
        return taskStatusesService.save(taskStatusData);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskStatusDTO show(@PathVariable Long id) {
        return taskStatusesService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskStatusDTO update(@RequestBody TaskStatusUpdateDTO taskStatusData,
                                @PathVariable Long id) {
        return taskStatusesService.updateById(taskStatusData, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        taskStatusesService.deleteById(id);
    }
}

