package languageTeacher.courses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoveTimeslotCommand {

    private Weekdays day;

    private LocalTime startTime;

}
