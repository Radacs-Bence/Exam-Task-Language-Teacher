package languageTeacher.teachers;

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

    public TeachersService(ModelMapper modelMapper, TeachersRepository teachersRepository) {
        this.modelMapper = modelMapper;
        this.teachersRepository = teachersRepository;
       /* teachersRepository.save(new Teacher("Gipsz Jakab"));
        teachersRepository.save(new Teacher("Gipsz Jónás"));
        teachersRepository.save(new Teacher("Kis Piroska"));*/
    }

    public List<TeacherDTO> listTeachers() {
        List<Teacher> teachers = teachersRepository.findAllWithLanguages();

        Type targetListType = new TypeToken<List<TeacherDTO>>(){}.getType();
        return modelMapper.map(teachers, targetListType);
    }

    public TeacherDTO findTeacherById(long id) {
        Teacher teacher = searchById(id);
        return modelMapper.map(teacher, TeacherDTO.class);
    }

    private Teacher searchById(long id) {
        Teacher teacher = teachersRepository.findById(id).orElseThrow(() ->new IllegalArgumentException("Teacher not found: " + id));
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
}
