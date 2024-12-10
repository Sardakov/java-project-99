package hexlet.code.app.component;

import hexlet.code.app.dto.TaskStatusesDTO.TaskStatusCreateDTO;
import hexlet.code.app.dto.UsersDTO.UserCreateDTO;
import hexlet.code.app.mapper.TaskStatusMapper;
import hexlet.code.app.mapper.UserMapper;
import hexlet.code.app.model.Label;
import hexlet.code.app.repository.LabelRepository;
import hexlet.code.app.repository.TaskStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import hexlet.code.app.repository.UserRepository;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private final TaskStatusRepository taskStatusRepository;

    @Autowired
    private final LabelRepository labelRepository;

    @Autowired
    private final TaskStatusMapper taskStatusMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var userData = new UserCreateDTO();
        userData.setEmail("hexlet@example.com");
        userData.setPassword("qwerty");
        var user = userMapper.map(userData);
        userRepository.save(user);

        createDefaultTaskStatuses();

        List<String> labelsList = new ArrayList<>();
        labelsList.add("bug");
        labelsList.add("feature");

        for (var label : labelsList) {
            var tsLabel = new Label();
            tsLabel.setName(label);
            labelRepository.save(tsLabel);
        }
    }

    private void createDefaultTaskStatuses() {
        List<TaskStatusCreateDTO> defaultStatuses = Arrays.asList(
                createStatus("Draft", "draft"),
                createStatus("To Review", "to_review"),
                createStatus("To Be Fixed", "to_be_fixed"),
                createStatus("To Publish", "to_publish"),
                createStatus("Published", "published")
        );

        for (TaskStatusCreateDTO statusDTO : defaultStatuses) {
            var status = taskStatusMapper.map(statusDTO);
            taskStatusRepository.save(status);
        }
    }

    private TaskStatusCreateDTO createStatus(String name, String slug) {
        var statusDTO = new TaskStatusCreateDTO();
        statusDTO.setName(name);
        statusDTO.setSlug(slug);
        return statusDTO;
    }
}
