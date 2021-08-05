package languageTeacher.teachers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class UpdateContactCommand {

    @NotNull
    @Size(min = 1)
    private String address;

    @NotNull
    @Email
    private String eMail;

    @NotNull
    @Size(min = 7)
    private String phone;



}
