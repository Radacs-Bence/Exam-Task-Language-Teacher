package languageTeacher.teachers;

import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Find all teachers",
            description = "Finds all teachers in repository, does not list courses of the teachers.")
    public List<TeacherDTO> listTeachers(){
        return teachersService.listTeachers();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find teachers by id",
            description = "Finds a teacher in the repository, does not list courses of the teacher.")
    public TeacherDTO findTeacherById(@PathVariable("id") Long id){
        return teachersService.findTeacherById(id);
    }

    @GetMapping("/{id}/courses")
    @Operation(summary = "Find teachers by id with courses",
            description = "Finds a teacher in the repository, with the id and name of the course.")
    public TeacherCourseDTO findTeacherByIdWithCourses(@PathVariable("id") Long id){
        return teachersService.findTeacherByIdWithCourses(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Save a teacher",
            description = "Saves a teacher into the repository with a name.")
    public TeacherDTO saveTeacher(@RequestBody CreateTeacherCommand command){
        return teachersService.saveTeacher(command);
    }

    @PostMapping("/{id}/languages")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Add language to teacher",
            description = "Adds a language to the teacher.")
    public TeacherDTO addLanguage(@PathVariable Long id, @RequestBody UpdateLanguageCommand command){
        return teachersService.addLanguage(id, command);
    }

    @PutMapping("/{id}/contact")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Modify contact info",
            description = "Modifys the contact information of the teacher.")
    public TeacherDTO modifyContact(@PathVariable Long id, @RequestBody UpdateContactCommand command){
        return teachersService.modifyContact(id, command);
    }

    @PutMapping("/{id}/courses")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Add course to teacher",
            description = "Adds a newly created course to the teacher.")
    public TeacherCourseDTO addCourse(@PathVariable Long id, @RequestBody CreateCourseCommand command){
        return teachersService.addCourse(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete teacher by id",
            description = "Deletes the teacher form the database.")
    public void deleteTeacher(@PathVariable Long id){
        teachersService.deleteTeacher(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete all teachers",
            description = "DELETES ALL TEACHERS! USE CAREFULLY!")
    public void deleteAll(){
        teachersService.deleteAll();
    }

    @DeleteMapping("/{id}/courses/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove course from teacher",
            description = "Removes the course from the teacher, however it's not deleted, it can get a different teacher.")
    public void removeTeacherCourse(@PathVariable Long id, @PathVariable Long courseId){
        teachersService.removeTeacherCourse(id, courseId);
    }
}
