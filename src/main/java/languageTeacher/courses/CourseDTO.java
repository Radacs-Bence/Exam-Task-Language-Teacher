package languageTeacher.courses;

import languageTeacher.Languages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {

    private Long id;

    private String name;

    private Languages language;

    private LocalDate start;

    private LocalDate end;
}
