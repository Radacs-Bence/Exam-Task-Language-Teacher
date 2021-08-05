package languageTeacher.courses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class CreateTimeslotCommand {

    @NotNull
    private Weekdays day;

    @NotNull
    private LocalTime startTime;

    @NotNull
    @Positive
    private int lengthInMinutes;
}
