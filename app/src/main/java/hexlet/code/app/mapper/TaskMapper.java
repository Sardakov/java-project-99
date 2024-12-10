package hexlet.code.app.mapper;

import hexlet.code.app.dto.TasksDTO.TaskCreateDTO;
import hexlet.code.app.dto.TasksDTO.TaskDTO;
import hexlet.code.app.dto.TasksDTO.TaskUpdateDTO;
import hexlet.code.app.model.Label;
import hexlet.code.app.model.Task;
import hexlet.code.app.model.TaskStatus;
import hexlet.code.app.model.User;
import hexlet.code.app.repository.LabelRepository;
import hexlet.code.app.repository.TaskStatusRepository;
import hexlet.code.app.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        uses = { JsonNullableMapper.class, ReferenceMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class TaskMapper {
    @Autowired
    private TaskStatusRepository taskStatusRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LabelRepository labelRepository;

    @Mapping(target = "status", source = "taskStatus", qualifiedByName = "statusSlug")
    @Mapping(target = "assignee", source = "assigneeId")
    @Mapping(target = "labels", source = "taskLabelIds")
    public abstract Task map(TaskCreateDTO dto);
    @Mapping(target = "status", source = "status.slug")
    @Mapping(target = "assigneeId", source = "assignee.id")
    @Mapping(target = "labelsId", source = "labels", qualifiedByName = "modelToDto")
    public abstract TaskDTO map(Task model);

    @Mapping(target = "labels", source = "taskLabelIds", qualifiedByName = "dtoToModel")
    @Mapping(target = "status", source = "taskStatus", qualifiedByName = "statusSlug")
    @Mapping(target = "assignee", source = "assigneeId", qualifiedByName = "userNull")
    public abstract void update(TaskUpdateDTO dto, @MappingTarget Task model);

    @Named("statusSlug")
    public TaskStatus satatusSlugModel(String slug) {
        return taskStatusRepository.findBySlug(slug)
                .orElseThrow();
    }

    @Named("userNull")
    public User userNull(Long id) {
        if (id == null) {
            return null;
        } else {
            return userRepository.findById(id).orElse(null);
        }
    }

    @Named("modelToDto")
    public Set<Long> modelToLabelIds(Set<Label> model) {
        var collectLabel =  model.stream()
                .map(v -> v.getId())
                .collect(Collectors.toSet());
        return collectLabel;
    }
    @Named("dtoToModel")
    public Set<Label> dtoToModel(Set<Long> model) {
        return new HashSet<>(labelRepository.findByIdIn(model));
    }

}

