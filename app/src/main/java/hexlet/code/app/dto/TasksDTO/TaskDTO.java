package hexlet.code.app.dto.TasksDTO;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class TaskDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("index")
    private Integer index;
    @JsonProperty("createdAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdAt;
    @JsonProperty("assignee_id")
    private Long assigneeId;
    @JsonProperty("title")
    private String name;
    @JsonProperty("content")
    private String description;
    @JsonProperty("status")
    private String status;
}
