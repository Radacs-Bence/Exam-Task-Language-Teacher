package languageTeacher.courses;

import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Timetables", uniqueConstraints = {@UniqueConstraint(columnNames = {"day", "start_time"})})
public class Timeslot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Weekdays day;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "length_in_min")
    private int lengthInMinutes;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Course course;

    public Timeslot(Weekdays day, LocalTime startTime, int lengthInMinutes) {
        this.day = day;
        this.startTime = startTime;
        this.lengthInMinutes = lengthInMinutes;
    }
}
