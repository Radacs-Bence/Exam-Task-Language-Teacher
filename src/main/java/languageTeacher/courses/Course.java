package languageTeacher.courses;

import languageTeacher.Languages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Courses")
public class Course {

    @Id
    @GeneratedValue(generator = "Course_Gen")
    @TableGenerator(name = "Course_Gen", table = "course_id_gen", pkColumnName = "id_gen ", pkColumnValue = "id_val")
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Languages language;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "course")
    private List<Timeslot> timetable;

    private LocalDate start;

    private LocalDate end;

    public void addTimeslot(Timeslot timeslot){
        if (timetable == null){
            timetable = new ArrayList<>();
        }
        timeslot.setCourse(this);
        timetable.add(timeslot);
    }


}
