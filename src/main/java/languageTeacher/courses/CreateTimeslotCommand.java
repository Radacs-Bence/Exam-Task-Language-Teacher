package languageTeacher.courses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

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
    @Schema(implementation = String.class, format = "hh:mm", example = "00:00")
    private LocalTime startTime;

    @Positive
    private int lengthInMinutes;
}
