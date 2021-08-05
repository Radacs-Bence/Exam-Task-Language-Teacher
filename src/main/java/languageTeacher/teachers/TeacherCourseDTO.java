package languageTeacher.teachers;

import languageTeacher.Languages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherCourseDTO {


    private Long id;

    private String name;

    private Set<Languages> languages;

    private ContactDTO contact;

    private List<CourseListDTO> courses;
}
