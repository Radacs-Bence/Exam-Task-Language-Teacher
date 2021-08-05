package languageTeacher.courses;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CoursesRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c from Course c left join fetch c.timetable where c.id = :id")
    Course findByIdWithTimetable(@Param("id") Long id);

}
