package courses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Timetable")
public class Timeslot {

    @Id
    @GeneratedValue(generator = "Time_Gen")
    @TableGenerator(name = "Time_Gen", table = "Time_id_gen", pkColumnName = "id_gen ", pkColumnValue = "id_val")
    private Long id;

    private Weekdays day;

    private LocalTime time;

    private int durationInMinutes;

    @ManyToOne
    private Course course;
}
