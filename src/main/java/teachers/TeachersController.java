package teachers;

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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTeacher(@PathVariable Long id){
        teachersService.deleteTeacher(id);
    }
}
