package languageTeacher.courses;

import languageTeacher.Languages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseTimetableDTO {

    private Long id;

    private String name;

    private Languages language;

    private List<TimeslotDTO> timetable;

    private LocalDate start;

    private LocalDate end;
}
