package hexlet.code.app.util;

import hexlet.code.app.model.Task;
import hexlet.code.app.model.TaskStatus;
import hexlet.code.app.model.User;
import hexlet.code.app.model.Label;
import hexlet.code.app.repository.TaskStatusRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import net.datafaker.Faker;
import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Getter
@Component
public class ModelGenerator {
    private Model<User> userModel;
    private Model<TaskStatus> taskStatusModel;
    private Model<Task> taskModel;
    private Model<Label> labelModel;

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Faker faker;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @PostConstruct
    private void init() {
        userModel = Instancio.of(User.class)
                .ignore(Select.field(User::getId))
                .supply(Select.field(User::getFirstName), () -> faker.name().firstName())
                .supply(Select.field(User::getLastName), () -> faker.name().lastName())
                .supply(Select.field(User::getEmail), () -> faker.internet().emailAddress())
                .supply(Select.field(User::getPasswordDigest), () -> faker.internet().password(3, 100))
                .toModel();

        taskStatusModel = Instancio.of(TaskStatus.class)
                .ignore(Select.field(TaskStatus::getId))
                .supply(Select.field(TaskStatus::getName), () -> faker.internet().username())
                .supply(Select.field(TaskStatus::getSlug), () -> faker.internet().slug())
                .toModel();

        taskModel = Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .supply(Select.field(Task::getIndex), () -> faker.number().randomDigitNotZero())
                .supply(Select.field(Task::getDescription), () -> faker.gameOfThrones().character())
                .toModel();

        labelModel = Instancio.of(Label.class)
                .ignore(Select.field(Label::getId))
                .supply(Select.field(Label::getName), () -> faker.gameOfThrones().character())
                .toModel();


        logGeneratedModels();
    }

//    private TaskStatus generateAndSaveTaskStatus() {
//        TaskStatus taskStatus = Instancio.create(taskStatusModel);
//        return taskStatusRepository.save(taskStatus); // Сохраняем в базе данных
//    }

    private void logGeneratedModels() {
        try {
            User generatedUser = Instancio.create(userModel);
            logger.info("Generated user model: {}", generatedUser.getCreatedAt());

            TaskStatus generatedTaskStatus = Instancio.create(taskStatusModel);
            logger.info("Generated taskStatus model: {}", generatedTaskStatus);

            Task generatedTask = Instancio.create(taskModel);
            logger.info("Generated Task model: {}", generatedTask);

            Label generatedLabel = Instancio.create(labelModel);
            logger.info("Generated Label model: {}", generatedLabel);
        } catch (Exception e) {
            logger.error("Failed to log generated models", e);
        }
    }
}
