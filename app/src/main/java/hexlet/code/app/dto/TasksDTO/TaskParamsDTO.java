package hexlet.code.app.dto.TasksDTO;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class TaskParamsDTO {
    private String titleCont;
    private Long assigneeId;
    private String status;
    private Long labelId;
}
