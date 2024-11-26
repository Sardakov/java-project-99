package hexlet.code.app.util;

import hexlet.code.app.model.TaskStatus;
import hexlet.code.app.model.User;
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

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Faker faker;

    @PostConstruct
    private void init() {
        userModel = Instancio.of(User.class)
                .ignore(Select.field(User::getId))
                .supply(Select.field(User::getEmail), () -> faker.internet().emailAddress())
                .supply(Select.field(User::getPasswordDigest), () -> faker.internet().password(3, 100))
                .toModel();

        taskStatusModel = Instancio.of(TaskStatus.class)
                .ignore(Select.field(TaskStatus::getId))
                .supply(Select.field(TaskStatus::getName), () -> faker.internet().username())
                .supply(Select.field(TaskStatus::getSlug), () -> faker.internet().slug())
                .toModel();

        logGeneratedModels();
    }

    private void logGeneratedModels() {
        try {
            User generatedUser = Instancio.create(userModel);
            logger.info("Generated user model: {}", generatedUser);

            TaskStatus generatedTaskStatus = Instancio.create(taskStatusModel);
            logger.info("Generated taskStatus model: {}", generatedTaskStatus);
        } catch (Exception e) {
            logger.error("Failed to log generated models", e);
        }
    }
}
