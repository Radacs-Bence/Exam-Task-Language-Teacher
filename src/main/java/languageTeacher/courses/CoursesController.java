package languageTeacher.courses;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CoursesController {

    private CoursesService coursesService;

    public CoursesController(CoursesService coursesService) {
        this.coursesService = coursesService;
    }

    @GetMapping
    public List<CourseDTO> listCourses(){
        return coursesService.listCourses();
    }

    @GetMapping("/{id}")
    public CourseDTO findCourseById(@PathVariable("id") Long id){
        return coursesService.findCourseById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseDTO saveCourse(@RequestBody CreateCourseCommand command){
        return coursesService.saveCourse(command);
    }

    @PutMapping("/{id}/duration")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CourseDTO modifyDuration(@PathVariable Long id, @RequestBody UpdateDurationCommand command){
        return coursesService.modifyDuration(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable Long id){
        coursesService.deleteCourse(id);
    }
}
