package languageTeacher.teachers;

import languageTeacher.Languages;
import languageTeacher.courses.Course;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "teacher")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Course> courses;

    public Teacher(String name) {
        this.name = name;
    }

    public void addLanguage(Languages language){
        if (languages == null) {
            languages = new HashSet<>();
        }
        languages.add(language);
    }

    public void addCourse(Course course){
        if (courses == null) {
            courses = new ArrayList<>();
        }
        course.setTeacher(this);
        courses.add(course);
    }

    public void removeCourse(Long courseId){
        Course course = courses.stream().filter(c -> c.getId() == courseId).findAny().get();
        course.setTeacher(null);
        courses.remove(course);
    }
}
