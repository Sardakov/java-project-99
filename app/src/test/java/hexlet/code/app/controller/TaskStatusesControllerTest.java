package hexlet.code.app.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import hexlet.code.app.dto.TaskStatusesDTO.TaskStatusDTO;
import hexlet.code.app.dto.TaskStatusesDTO.TaskStatusUpdateDTO;
import hexlet.code.app.mapper.TaskStatusMapper;
import hexlet.code.app.model.TaskStatus;
import hexlet.code.app.repository.TaskStatusRepository;
import hexlet.code.app.util.ModelGenerator;
import net.datafaker.Faker;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskStatusesControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelGenerator modelGenerator;

    @Autowired
    private ObjectMapper om;

    private JwtRequestPostProcessor token;

    @Autowired
    private TaskStatusMapper taskStatusMapper;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    private TaskStatus testTaskStatus;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                .apply(springSecurity())
                .build();

        token = jwt().jwt(builder -> builder.subject("hexlet@example.com"));

        testTaskStatus = Instancio.of(modelGenerator.getTaskStatusModel())
                .create();
    }

    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(get("/api/task_statuses").with(jwt()))
                .andExpect(status().isOk());
    }

    @Test
    public void testShow() throws Exception {
        taskStatusRepository.save(testTaskStatus);
        mockMvc.perform(get("/api/task_statuses/" + testTaskStatus.getId()).with(jwt()))
                .andExpect(status().isOk());

        var status = taskStatusRepository.findById(testTaskStatus.getId()).get();
        assertThat(status.getSlug()).isEqualTo(testTaskStatus.getSlug());

    }

    @Test
    public void testCreate() throws Exception {
        var data = taskStatusMapper.map(testTaskStatus);
        var request = post("/api/task_statuses")
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));
        mockMvc.perform(request)
                .andExpect(status().isCreated());

        var taskStatus = taskStatusRepository.findBySlug(data.getSlug()).get();

        assertNotNull(taskStatus);
        assertThat(taskStatus.getName()).isEqualTo(data.getName());
        assertThat(taskStatus.getSlug()).isEqualTo(data.getSlug());
    }

    @Test
    public void testUpdate() throws Exception {
        taskStatusRepository.save(testTaskStatus);

        String uniqName = "uniq-name-" + UUID.randomUUID();

        var data = new TaskStatusUpdateDTO();
        data.setName(JsonNullable.of(uniqName));

        var request = put("/api/task_statuses/" + testTaskStatus.getId())
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));
        mockMvc.perform(request)
                .andExpect(status().isOk());

        var taskStatus = taskStatusRepository.findById(testTaskStatus.getId()).get();
        assertThat(taskStatus.getName()).isEqualTo((uniqName));
    }

    @Test
    public void testDeleteUnauthorized() throws Exception {
        taskStatusRepository.save(testTaskStatus);

        var request = delete("/api/task_statuses/" + testTaskStatus.getId()); // Без токена
        mockMvc.perform(request)
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testDeleteAuthorized() throws Exception {
        taskStatusRepository.save(testTaskStatus);

        var request = delete("/api/task_statuses/" + testTaskStatus.getId())
                .with(jwt());
        mockMvc.perform(request)
                .andExpect(status().isNoContent());
    }
}
