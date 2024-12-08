package hexlet.code.app.dto.LabelsDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LabelCreateDTO {
    @NotBlank
    @Size(min = 3)
    private String name;
}
