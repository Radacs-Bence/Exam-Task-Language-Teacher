package teachers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {

    private String address;

    private String eMail;

    private String phone;
}
