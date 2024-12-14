package hexlet.code.app.service;

import hexlet.code.app.dto.TaskStatusesDTO.TaskStatusCreateDTO;
import hexlet.code.app.dto.TaskStatusesDTO.TaskStatusDTO;
import hexlet.code.app.dto.TaskStatusesDTO.TaskStatusUpdateDTO;
import hexlet.code.app.exception.ResourceNotFoundException;
import hexlet.code.app.mapper.TaskStatusMapper;
import hexlet.code.app.repository.TaskStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskStatusesService {
    private final TaskStatusRepository taskStatusRepository;
    private final TaskStatusMapper taskStatusMapper;

    public List<TaskStatusDTO> findAll() {
        var taskStatus = taskStatusRepository.findAll();
        return taskStatus.stream()
                .map(taskStatusMapper::map)
                .toList();
    }

    public TaskStatusDTO save(TaskStatusCreateDTO taskStatusData) {
        var taskStatus = taskStatusMapper.map(taskStatusData);
        taskStatusRepository.save(taskStatus);
        return taskStatusMapper.map(taskStatus);
    }

    public TaskStatusDTO findById(Long id) {
        var taskStatus = taskStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        return taskStatusMapper.map(taskStatus);
    }

    public TaskStatusDTO updateById(TaskStatusUpdateDTO taskStatusData, Long id) {
        var taskStatus = taskStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        taskStatusMapper.update(taskStatusData, taskStatus);
        taskStatusRepository.save(taskStatus);
        return taskStatusMapper.map(taskStatus);
    }

    public void deleteById(Long id) {
        taskStatusRepository.deleteById(id);
    }


}
