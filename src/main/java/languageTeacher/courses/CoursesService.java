package languageTeacher.courses;


import languageTeacher.teachers.Teacher;
import languageTeacher.teachers.TeachersRepository;
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
    private TeachersRepository teachersRepository;

    public CoursesService(ModelMapper modelMapper, CoursesRepository coursesRepository, TeachersRepository teachersRepository) {
        this.modelMapper = modelMapper;
        this.coursesRepository = coursesRepository;
        this.teachersRepository = teachersRepository;
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

    public void deleteAll() {
        coursesRepository.deleteAll();
    }

    public CourseTimetableDTO findCourseByIdWithTimetable(Long id) {
        Course course = coursesRepository.findByIdWithTimetable(id);
        return modelMapper.map(course, CourseTimetableDTO.class);
    }

    @Transactional
    public CourseTimetableDTO addTimeslot(Long id, CreateTimeslotCommand command) {
        Course course = coursesRepository.findByIdWithTimetable(id);

        course.addTimeslot(new Timeslot(command.getDay(), command.getStartTime(), command.getLengthInMinutes()));

        return modelMapper.map(course, CourseTimetableDTO.class);
    }

    @Transactional
    public void removeTimeslot(Long id, Long timeSlotId) {
        Course course = coursesRepository.findByIdWithTimetable(id);

        course.removeTimeslot(timeSlotId);
    }

    @Transactional
    public CourseDTO modifyTeacher(Long id, Long teacherId) {
        Teacher teacher = teachersRepository.findById(teacherId).orElseThrow(() -> new IllegalArgumentException("Teacher not found: " + id));
        Course course = searchById(id);

        course.assingTeacher(teacher);

        return modelMapper.map(course, CourseDTO.class);
    }


    private Course searchById(long id) {
        return coursesRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Course not found: " + id));
    }

}
