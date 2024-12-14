package hexlet.code.app.component;

import hexlet.code.app.dto.TaskStatusesDTO.TaskStatusCreateDTO;
import hexlet.code.app.dto.UsersDTO.UserCreateDTO;
import hexlet.code.app.mapper.TaskStatusMapper;
import hexlet.code.app.mapper.UserMapper;
import hexlet.code.app.model.Label;
import hexlet.code.app.model.Task;
import hexlet.code.app.model.TaskStatus;
import hexlet.code.app.repository.LabelRepository;
import hexlet.code.app.repository.TaskRepository;
import hexlet.code.app.repository.TaskStatusRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import hexlet.code.app.repository.UserRepository;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @Autowired
    private Faker faker;

    @Autowired
    private final TaskRepository taskRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var userData = new UserCreateDTO();
        userData.setEmail("hexlet@example.com");
        userData.setPassword("qwerty");
        var user = userMapper.map(userData);
        userRepository.save(user);

        Map<String, String> statusMap = new HashMap<>();

        statusMap.put("Draft", "draft");
        statusMap.put("ToReview", "to_review");
        statusMap.put("ToBeFixed", "to_be_fixed");
        statusMap.put("ToPublish", "to_publish");
        statusMap.put("Published", "published");
        for (String key: statusMap.keySet()) {
            var tsStatus = new TaskStatus();
            tsStatus.setName(key);
            tsStatus.setSlug(statusMap.get(key));
            taskStatusRepository.save(tsStatus);

        }


        List<String> labelsList = new ArrayList<>();
        labelsList.add("bug");
        labelsList.add("feature");

        for (var label : labelsList) {
            var tsLabel = new Label();
            tsLabel.setName(label);
            labelRepository.save(tsLabel);
        }

        var statuses = taskStatusRepository.findAll();
        var labels = labelRepository.findAll();
        for (int i = 0; i < 5; i++) {
            var task = new Task();
            task.setStatus(statuses.get(i));
            task.setName(faker.name().name());
            task.setDescription(faker.lorem().paragraph());
            task.setLabels(Set.of(labels.get(0)));
            task.setAssignee(userRepository.findByEmail("hexlet@example.com").get());
            taskRepository.save(task);
        }
    }
}
