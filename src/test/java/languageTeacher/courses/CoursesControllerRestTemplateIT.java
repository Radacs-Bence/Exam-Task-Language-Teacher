package languageTeacher.courses;

import languageTeacher.Languages;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CoursesControllerRestTemplateIT {


    @Autowired
    TestRestTemplate template;

    @Autowired
    CoursesService coursesService;

    @BeforeEach
    void init() {
        coursesService.saveCourse(new CreateCourseCommand("Angol b2", Languages.ENG));
        coursesService.saveCourse(new CreateCourseCommand("Angol c1", Languages.ENG));
        coursesService.saveCourse(new CreateCourseCommand("Német b2", Languages.DEU));
    }

    @AfterEach
    void clean(){
        coursesService.deleteAll();
    }

    @Test
    void listCoursesTest() {
        List<CourseDTO> courses = template.exchange("/api/courses",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<CourseDTO>>() {
                        })
                .getBody();

        assertThat(courses)
                .hasSize(3)
                .extracting(CourseDTO::getName)
                .containsExactly("Angol b2", "Angol c1", "Német b2");
    }

    @Test
    void findCourseByIdTest() {
        CourseDTO courseCreated = template.postForObject("/api/courses",
                new CreateCourseCommand("Olasz c1", Languages.ITA),
                CourseDTO.class);

        CourseDTO courseFound = template.exchange("/api/courses/{id}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<CourseDTO>() {
                        },
                        courseCreated.getId())
                .getBody();

        assertThat(courseFound.getName()).isEqualTo("Olasz c1");
    }

    @Test
    void saveCourseTest() {
        CourseDTO course = template.postForObject("/api/courses",
                new CreateCourseCommand("Olasz c1", Languages.ITA),
                CourseDTO.class);

        assertThat(course.getName()).isEqualTo("Olasz c1");
    }

    @Test
    void modifyDurationTest() {
        CourseDTO courseCreated = template.postForObject("/api/courses",
                new CreateCourseCommand("Olasz b3", Languages.ITA),
                CourseDTO.class);

        template.put("/api/courses/{id}/duration",
                new UpdateDurationCommand(LocalDate.of(2020, 01, 01), LocalDate.now()),
                courseCreated.getId());

        CourseDTO courseFound = template.exchange("/api/courses/{id}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<CourseDTO>() {
                        },
                        courseCreated.getId())
                .getBody();

        assertThat(courseFound.getStart()).isEqualTo(LocalDate.of(2020, 01, 01));
    }

    @Test
    void deleteCourseTest() {
        CourseDTO course = template.postForObject("/api/courses",
                new CreateCourseCommand("Olasz b3", Languages.ITA),
                CourseDTO.class);

        assertThat(course.getName()).isEqualTo("Olasz b3");

        template.delete("/api/courses/{id}",
                course.getId());

        List<CourseDTO> courses = template.exchange("/api/courses",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<CourseDTO>>() {
                        })
                .getBody();

        assertThat(courses)
                .hasSize(3)
                .extracting(CourseDTO::getName)
                .containsExactly("Angol b2", "Angol c1", "Német b2");
    }



    @Test
    void addTimeslotTest() {
        CourseDTO courseCreated = template.postForObject("/api/courses",
                new CreateCourseCommand("Olasz c1", Languages.ITA),
                CourseDTO.class);

        CourseTimetableDTO courseFound = template.postForObject("/api/courses/{id}/timetable",
                new CreateTimeslotCommand(Weekdays.SAT, LocalTime.now(), 90),
                CourseTimetableDTO.class,
                courseCreated.getId());

        assertThat(courseFound.getTimetable())
                .hasSize(1)
                .extracting(TimeslotDTO::getDay)
                .contains(Weekdays.SAT);
    }

    @Test
    void deleteTimeslotTest() {
        CourseDTO courseCreated = template.postForObject("/api/courses",
                new CreateCourseCommand("Olasz c1", Languages.ITA),
                CourseDTO.class);

        template.postForObject("/api/courses/{id}/timetable",
                new CreateTimeslotCommand(Weekdays.MON, LocalTime.now(), 90),
                CourseTimetableDTO.class,
                courseCreated.getId());

        CourseTimetableDTO courseTimetable = template.postForObject("/api/courses/{id}/timetable",
                new CreateTimeslotCommand(Weekdays.SAT, LocalTime.now(), 90),
                CourseTimetableDTO.class,
                courseCreated.getId());

        template.delete("/api/courses/{id}/timetable/{timeSlotId}",
                courseTimetable.getId(),
                courseTimetable.getTimetable().get(0).getId());

        CourseTimetableDTO courseFound = template.getForObject("/api/courses/{id}/timetable",
                CourseTimetableDTO.class,
                courseCreated.getId());

        assertThat(courseFound.getTimetable())
                .hasSize(1)
                .extracting(TimeslotDTO::getDay)
                .contains(Weekdays.SAT);
    }





}
