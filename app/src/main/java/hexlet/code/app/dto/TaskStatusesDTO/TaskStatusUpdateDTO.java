package hexlet.code.app.dto.TaskStatusesDTO;

import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Setter
@Getter
public class TaskStatusUpdateDTO {
    private JsonNullable<String> name;

    private JsonNullable<String> slug;

}
