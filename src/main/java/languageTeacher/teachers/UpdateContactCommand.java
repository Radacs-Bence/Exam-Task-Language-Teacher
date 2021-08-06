package languageTeacher.teachers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class UpdateContactCommand {

    @NotBlank
    private String address;

    @NotBlank
    @Email
    private String eMail;

    @NotBlank
    @Size(min = 7)
    private String phone;



}
