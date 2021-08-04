package languageTeacher.courses;

import org.springframework.data.jpa.repository.JpaRepository;
import languageTeacher.teachers.Teacher;

public interface CoursesRepository extends JpaRepository<Teacher, Long> {
}
