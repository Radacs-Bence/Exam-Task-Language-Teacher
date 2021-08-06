package languageTeacher.courses;

import languageTeacher.Languages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class CreateCourseCommand {

    @NotBlank
    private String name;

    @NotNull
    private Languages language;
}
