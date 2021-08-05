package languageTeacher.courses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTimeslotCommand {

    private Weekdays day;

    private LocalTime startTime;

    private int lengthInMinutes;
}
