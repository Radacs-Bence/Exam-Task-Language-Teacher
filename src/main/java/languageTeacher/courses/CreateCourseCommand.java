package languageTeacher.courses;

import languageTeacher.Languages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCourseCommand {

    private String name;

    private Languages language;
}
