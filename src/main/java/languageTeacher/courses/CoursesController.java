package languageTeacher.courses;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
@Tag( name = "Operations on courses and their timetables")
public class CoursesController {

    private CoursesService coursesService;

    public CoursesController(CoursesService coursesService) {
        this.coursesService = coursesService;
    }

    @GetMapping
    @Operation(summary = "Find all courses",
            description = "Lists the courses in the repository.")
    public List<CourseDTO> listCourses(){
        return coursesService.listCourses();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find course by id",
            description = "Finds the course based on id, without timetable.")
    public CourseDTO findCourseById(@PathVariable("id") Long id){
        return coursesService.findCourseById(id);
    }

    @GetMapping("/{id}/timetable")
    @Operation(summary = "Find course by id, with timetable",
            description = "Finds the course based on id with timetable, but without Teacher.")
    public CourseTimetableDTO findCourseTimetable(@PathVariable("id") Long id){
        return coursesService.findCourseByIdWithTimetable(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a course",
            description = "Creates a course, with name and language.")
    public CourseDTO saveCourse(@RequestBody @Valid CreateCourseCommand command){
        return coursesService.saveCourse(command);
    }


    @PostMapping("/{id}/timetable")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Adds a timeslot to the course",
            description = "Makes a timeslot and adds it to the course.")
    public CourseTimetableDTO addTimeslot(@PathVariable("id") Long id, @RequestBody @Valid CreateTimeslotCommand command){
        return coursesService.addTimeslot(id, command);
    }

    @PutMapping("/{id}/duration")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Find course by id, with timetable",
            description = "Finds the course based on id with timetable, but without Teacher.")
    public CourseDTO modifyDuration(@PathVariable Long id, @RequestBody @Valid UpdateDurationCommand command){
        return coursesService.modifyDuration(id, command);
    }

    @PutMapping("/{id}/teacher/{teacherId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Change the teacher of the course",
            description = "Changes the teacher for the course, removing it from the old one.")
    public CourseDTO modifyTeacher(@PathVariable Long id, @PathVariable Long teacherId){
        return coursesService.modifyTeacher(id, teacherId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete course",
            description = "Deletes the course by id.")
    public void deleteCourse(@PathVariable Long id){
        coursesService.deleteCourse(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete all courses",
            description = "DELETES ALL COURSES! USE CAREFULLY!")
    public void deleteAll(){
        coursesService.deleteAll();
    }

    @DeleteMapping ("/{id}/timetable/{timeSlotId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove timeslot",
            description = "Removes a timeslot frm the course and deletes it.")
    public void removeTimeslot(@PathVariable("id") Long id , @PathVariable("timeSlotId") Long timeSlotId){
        coursesService.removeTimeslot(id, timeSlotId);
    }
}
