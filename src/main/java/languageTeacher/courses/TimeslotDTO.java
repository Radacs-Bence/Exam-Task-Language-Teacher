package languageTeacher.courses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeslotDTO {

    private Long id;

    private Weekdays day;

    private LocalTime time;

    private int durationInMinutes;
}
