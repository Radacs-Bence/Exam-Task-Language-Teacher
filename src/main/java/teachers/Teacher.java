package teachers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Teachers")
public class Teacher {

    @Id
    @GeneratedValue(generator = "Teacher_Gen")
    @TableGenerator(name = "Teacher_Gen", table = "teach_id_gen", pkColumnName = "id_gen ", pkColumnValue = "id_val")
    private Long id;

    private String name;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Languages> languages;

    @Embedded
    private Contact contact;

    public Teacher(String name) {
        this.name = name;
    }

    public void addLanguage(Languages language){
        if (languages == null) {
            languages = new HashSet<>();
        }
        languages.add(language);
    }

}
