package languageTeacher.courses;

import languageTeacher.Languages;
import lombok.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Languages language;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "course", orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Timeslot> timetable;

    private LocalDate start;

    private LocalDate end;

    public Course(String name, Languages language) {
        this.name = name;
        this.language = language;
    }

    public void addTimeslot(Timeslot timeslot){
        if (timetable == null){
            timetable = new ArrayList<>();
        }
        timeslot.setCourse(this);
        timetable.add(timeslot);
    }

    public void removeTimeslot(Long timeSlotId){
        Timeslot timeslot = timetable.stream().filter(t -> t.getId() == timeSlotId).findAny().get();
        timetable.remove(timeslot);
    }


}
