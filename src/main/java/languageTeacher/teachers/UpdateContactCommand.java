package languageTeacher.teachers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateContactCommand {


    private String address;
    private String eMail;
    private String phone;



}
