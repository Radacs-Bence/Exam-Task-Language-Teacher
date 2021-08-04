package languageTeacher.courses;


import languageTeacher.teachers.TeacherDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class CoursesService {

    private ModelMapper modelMapper;
    private CoursesRepository coursesRepository;

    public CoursesService(ModelMapper modelMapper, CoursesRepository coursesRepository) {
        this.modelMapper = modelMapper;
        this.coursesRepository = coursesRepository;
    }

    public List<CourseDTO> listCourses() {
        List<Course> courses = coursesRepository.findAll();

        Type targetListType = new TypeToken<List<CourseDTO>>() {
        }.getType();
        return modelMapper.map(courses, targetListType);
    }


    public CourseDTO findCourseById(Long id) {
        Course course = searchById(id);
        return modelMapper.map(course, CourseDTO.class);
    }

    private Course searchById(long id) {
        Course course = coursesRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Course not found: " + id));
        return course;
    }


    public CourseDTO saveCourse(CreateCourseCommand command) {
        Course course = new Course(command.getName(), command.getLanguage());
        coursesRepository.save(course);
        return modelMapper.map(course, CourseDTO.class);
    }

    @Transactional
    public CourseDTO modifyDuration(Long id, UpdateDurationCommand command) {
        Course course = searchById(id);
        course.setStart(command.getStart());
        course.setEnd(command.getEnd());
        return modelMapper.map(course, CourseDTO.class);
    }

    public void deleteCourse(Long id) {
        coursesRepository.deleteById(id);
    }
}
