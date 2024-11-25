package hexlet.code.app.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class TaskStatusDTO {
    private Long id;

    private String slug;

    private String name;

    private LocalDate createdAt;
}
