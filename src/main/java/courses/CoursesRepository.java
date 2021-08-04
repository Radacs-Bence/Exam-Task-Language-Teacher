package courses;

import org.springframework.data.jpa.repository.JpaRepository;
import teachers.Teacher;

public interface CoursesRepository extends JpaRepository<Teacher, Long> {
}
