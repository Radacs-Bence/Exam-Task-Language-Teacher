package languageTeacher.courses;

import lombok.*;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeslotDTO {

    private Long id;

    private Weekdays day;

    private LocalTime startTime;

    private int durationInMinutes;
}
