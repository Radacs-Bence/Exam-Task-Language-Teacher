package languageTeacher.courses;


import languageTeacher.Languages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("/{id}/timetable")
    public CourseTimetableDTO findCourseTimetable(@PathVariable("id") Long id){
        return coursesService.findCourseByIdWithTimetable(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseDTO saveCourse(@RequestBody CreateCourseCommand command){
        return coursesService.saveCourse(command);
    }


    @PostMapping("/{id}/timetable")
    @ResponseStatus(HttpStatus.CREATED)
    public CourseTimetableDTO addTimeslot(@PathVariable("id") Long id, @RequestBody CreateTimeslotCommand command){
        return coursesService.addTimeslot(id, command);
    }

    @PutMapping("/{id}/duration")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CourseDTO modifyDuration(@PathVariable Long id, @RequestBody UpdateDurationCommand command){
        return coursesService.modifyDuration(id, command);
    }

    @PutMapping("/{id}/teacher/{teacherId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CourseDTO modifyTeacher(@PathVariable Long id, @PathVariable Long teacherId){
        return coursesService.modifyTeacher(id, teacherId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable Long id){
        coursesService.deleteCourse(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll(){
        coursesService.deleteAll();
    }

    @DeleteMapping ("/{id}/timetable/{timeSlotId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTimeslot(@PathVariable("id") Long id , @PathVariable("timeSlotId") Long timeSlotId){
        coursesService.removeTimeslot(id, timeSlotId);
    }
}
