package hexlet.code.app.dto.TasksDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;


@Setter
@Getter
public class TaskUpdateDTO {
    @NotBlank
    @JsonProperty("title")
    private JsonNullable<String> name;

    @JsonProperty("content")
    private JsonNullable<String> description;

    @NotBlank
    @JsonProperty("status")
    private JsonNullable<String> taskStatus;

    @JsonProperty("assignee_id")
    private JsonNullable<Long> assigneeId;
}

