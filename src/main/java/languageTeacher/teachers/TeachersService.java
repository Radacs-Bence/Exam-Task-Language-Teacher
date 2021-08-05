package languageTeacher.teachers;

import languageTeacher.courses.Course;
import languageTeacher.courses.CoursesRepository;
import languageTeacher.courses.CreateCourseCommand;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class TeachersService {


    private ModelMapper modelMapper;
    private TeachersRepository teachersRepository;
    private CoursesRepository coursesRepository;

    public TeachersService(ModelMapper modelMapper, TeachersRepository teachersRepository, CoursesRepository coursesRepository) {
        this.modelMapper = modelMapper;
        this.teachersRepository = teachersRepository;
       /* teachersRepository.save(new Teacher("Gipsz Jakab"));
        teachersRepository.save(new Teacher("Gipsz Jónás"));
        teachersRepository.save(new Teacher("Kis Piroska"));*/
        this.coursesRepository = coursesRepository;
    }

    public List<TeacherDTO> listTeachers() {
        List<Teacher> teachers = teachersRepository.findAllWithLanguages();

        Type targetListType = new TypeToken<List<TeacherDTO>>() {
        }.getType();
        return modelMapper.map(teachers, targetListType);
    }

    public TeacherDTO findTeacherById(long id) {
        Teacher teacher = searchById(id);
        return modelMapper.map(teacher, TeacherDTO.class);
    }

    private Teacher searchById(long id) {
        Teacher teacher = teachersRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Teacher not found: " + id));
        return teacher;
    }

    public TeacherDTO saveTeacher(CreateTeacherCommand command) {
        Teacher teacher = new Teacher(command.getName());
        teachersRepository.save(teacher);
        return modelMapper.map(teacher, TeacherDTO.class);
    }

    @Transactional
    public TeacherDTO addLanguage(Long id, UpdateLanguageCommand command) {
        Teacher teacher = searchById(id);
        teacher.addLanguage(command.getLanguage());
        return modelMapper.map(teacher, TeacherDTO.class);
    }

    @Transactional
    public TeacherDTO modifyContact(Long id, UpdateContactCommand command) {
        Teacher teacher = searchById(id);
        teacher.setContact(new Contact(command.getAddress(), command.getEMail(), command.getPhone()));
        return modelMapper.map(teacher, TeacherDTO.class);
    }

    public void deleteTeacher(Long id) {
        teachersRepository.deleteById(id);
    }

    public void deleteAll() {
        teachersRepository.deleteAll();
    }

    public TeacherCourseDTO findTeacherByIdWithCourses(Long id) {
        Teacher teacher = teachersRepository.findByIdWithCourses(id);
        return modelMapper.map(teacher, TeacherCourseDTO.class);
    }

    @Transactional
    public TeacherCourseDTO addCourse(Long id, CreateCourseCommand command) {
        Teacher teacher = teachersRepository.findByIdWithCourses(id);
        teacher.addCourse(new Course(command.getName(), command.getLanguage()));
        Teacher teacher2 = teachersRepository.findByIdWithCourses(id);
        return modelMapper.map(teacher2, TeacherCourseDTO.class);
    }

    @Transactional
    public TeacherCourseDTO removeTeacherCourse(Long id, Long courseId) {
        Teacher teacher = teachersRepository.findByIdWithCourses(id);

        teacher.removeCourse(courseId);
        return modelMapper.map(teacher, TeacherCourseDTO.class);
    }
}
