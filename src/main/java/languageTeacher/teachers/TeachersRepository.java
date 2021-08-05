package languageTeacher.teachers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeachersRepository extends JpaRepository<Teacher, Long> {

    @Query("SELECT DISTINCT t from Teacher t left join fetch t.languages")
    List<Teacher> findAllWithLanguages();

    @Query("SELECT t from Teacher t left join fetch t.courses where t.id = :id")
    Teacher findByIdWithCourses(@Param("id") Long id);
}
