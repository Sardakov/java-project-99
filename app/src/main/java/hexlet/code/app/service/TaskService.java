package hexlet.code.app.service;

import hexlet.code.app.dto.TasksDTO.TaskCreateDTO;
import hexlet.code.app.dto.TasksDTO.TaskDTO;
import hexlet.code.app.dto.TasksDTO.TaskParamsDTO;
import hexlet.code.app.dto.TasksDTO.TaskUpdateDTO;
import hexlet.code.app.exception.ResourceNotFoundException;
import hexlet.code.app.mapper.TaskMapper;
import hexlet.code.app.repository.TaskRepository;
import hexlet.code.app.specification.TaskSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final TaskSpecification specBuilder;

    public List<TaskDTO> findAll(TaskParamsDTO params) {
        var spec = specBuilder.build(params);
        var task = taskRepository.findAll(spec);
        return task.stream()
                .map(taskMapper::map)
                .toList();
    }

    public TaskDTO findById(Long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        return taskMapper.map(task);
    }

    public TaskDTO save(TaskCreateDTO taskData) {
        var task = taskMapper.map(taskData);
        taskRepository.save(task);
        return taskMapper.map(task);
    }

    public TaskDTO updateById(TaskUpdateDTO taskData, Long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        taskMapper.update(taskData, task);
        taskRepository.save(task);
        return taskMapper.map(task);
    }

    public void deleteById(Long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        taskRepository.deleteById(id);
    }
}
