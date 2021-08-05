package languageTeacher.teachers;

import languageTeacher.Languages;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ElementCollection
    @Column(name = "language")
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "languages", joinColumns = @JoinColumn (name = "teach_id"))
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
