package languageTeacher.teachers;

import languageTeacher.courses.CreateCourseCommand;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeachersController {

    private TeachersService teachersService;

    public TeachersController(TeachersService teachersService) {
        this.teachersService = teachersService;
    }

    @GetMapping
    public List<TeacherDTO> listTeachers(){
        return teachersService.listTeachers();
    }

    @GetMapping("/{id}")
    public TeacherDTO findTeacherById(@PathVariable("id") Long id){
        return teachersService.findTeacherById(id);
    }

    @GetMapping("/{id}/courses")
    public TeacherCourseDTO findTeacherByIdWithCourses(@PathVariable("id") Long id){
        return teachersService.findTeacherByIdWithCourses(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherDTO saveTeacher(@RequestBody CreateTeacherCommand command){
        return teachersService.saveTeacher(command);
    }

    @PostMapping("/{id}/languages")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TeacherDTO addLanguage(@PathVariable Long id, @RequestBody UpdateLanguageCommand command){
        return teachersService.addLanguage(id, command);
    }

    @PutMapping("/{id}/contact")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TeacherDTO modifyContact(@PathVariable Long id, @RequestBody UpdateContactCommand command){
        return teachersService.modifyContact(id, command);
    }

    @PutMapping("/{id}/courses")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TeacherCourseDTO addCourse(@PathVariable Long id, @RequestBody CreateCourseCommand command){
        return teachersService.addCourse(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTeacher(@PathVariable Long id){
        teachersService.deleteTeacher(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll(){
        teachersService.deleteAll();
    }

    @DeleteMapping("/{id}/courses/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTeacherCourse(@PathVariable Long id, @PathVariable Long courseId){
        teachersService.removeTeacherCourse(id, courseId);
    }
}
