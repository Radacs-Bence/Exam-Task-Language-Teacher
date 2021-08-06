package languageTeacher.teachers;

import languageTeacher.Languages;
import languageTeacher.courses.CoursesService;
import languageTeacher.courses.CreateCourseCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TeachersControllerRestTemplateIT {

    @Autowired
    TestRestTemplate template;

    @Autowired
    TeachersService teachersService;

    @Autowired
    CoursesService coursesService;

    @BeforeEach
    void init() {
        teachersService.deleteAll();
        coursesService.deleteAll();
        teachersService.saveTeacher(new CreateTeacherCommand("Gipsz Jakab"));
        teachersService.saveTeacher(new CreateTeacherCommand("Gipsz Jónás"));
        teachersService.saveTeacher(new CreateTeacherCommand("Kis Piroska"));
    }

    @AfterEach
    void clean() {
        teachersService.deleteAll();
        coursesService.deleteAll();
    }

    @Test
    void listTeachersTest() {
        List<TeacherDTO> teachers = template.exchange("/api/teachers",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<TeacherDTO>>() {
                        })
                .getBody();

        assertThat(teachers)
                .hasSize(3)
                .extracting(TeacherDTO::getName)
                .containsExactly("Gipsz Jakab", "Gipsz Jónás", "Kis Piroska");
    }

    @Test
    void findTeacherByIdTest() {
        TeacherDTO teacherCreated = template.postForObject("/api/teachers",
                new CreateTeacherCommand("Nagy Piroska"),
                TeacherDTO.class);

        TeacherDTO teacherFound = template.exchange("/api/teachers/{id}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<TeacherDTO>() {
                        },
                        teacherCreated.getId())
                .getBody();

        assertThat(teacherFound.getName()).isEqualTo("Nagy Piroska");
    }

    @Test
    void saveTeacherTest() {
        TeacherDTO teacher = template.postForObject("/api/teachers",
                new CreateTeacherCommand("Nagy Piroska"),
                TeacherDTO.class);

        assertThat(teacher.getName()).isEqualTo("Nagy Piroska");
    }

    @Test
    void addLanguageTest() {
        TeacherDTO teacherCreated = template.postForObject("/api/teachers",
                new CreateTeacherCommand("Nagy Piroska"),
                TeacherDTO.class);

        TeacherDTO teacherFound = template.postForObject("/api/teachers/{id}/languages",
                new UpdateLanguageCommand(Languages.ESP),
                TeacherDTO.class,
                teacherCreated.getId());

        assertThat(teacherFound.getLanguages()).contains(Languages.ESP);
    }

    @Test
    void modifyContactTest() {
        TeacherDTO teacherCreated = template.postForObject("/api/teachers",
                new CreateTeacherCommand("Nagy Piroska"),
                TeacherDTO.class);

        template.put("/api/teachers/{id}/contact",
                new UpdateContactCommand("Van utca", "blabla@ize.com", "201234567"),
                teacherCreated.getId());

        TeacherDTO teacherFound = template.exchange("/api/teachers/{id}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<TeacherDTO>() {
                        },
                        teacherCreated.getId())
                .getBody();

        assertThat(teacherFound.getContact().getAddress()).isEqualTo("Van utca");
    }

    @Test
    void deleteTeacherTest() {
        TeacherDTO teacher = template.postForObject("/api/teachers",
                new CreateTeacherCommand("Nagy Piroska"),
                TeacherDTO.class);

        assertThat(teacher.getName()).isEqualTo("Nagy Piroska");

        template.delete("/api/teachers/{id}",
                teacher.getId());

        List<TeacherDTO> teachers = template.exchange("/api/teachers",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<TeacherDTO>>() {
                        })
                .getBody();

        assertThat(teachers)
                .hasSize(3)
                .extracting(TeacherDTO::getName)
                .containsExactly("Gipsz Jakab", "Gipsz Jónás", "Kis Piroska");
    }



    @Test
    void addCourseTest(){
        TeacherDTO teacher = template.postForObject("/api/teachers",
                new CreateTeacherCommand("Nagy Piroska"),
                TeacherDTO.class);

        template.put("/api/teachers/{id}/courses",
                new CreateCourseCommand("Olasz c1", Languages.ITA),
                teacher.getId());

        TeacherCourseDTO teacherFound = template.getForObject("/api/teachers/{id}/courses",
                TeacherCourseDTO.class,
                teacher.getId());

        assertThat(teacherFound.getCourses())
                .extracting(CourseListDTO::getName)
                .contains("Olasz c1");
    }

    @Test
    void removeCourseTest(){
        TeacherDTO teacherCreated = template.postForObject("/api/teachers",
                new CreateTeacherCommand("Nagy Piroska"),
                TeacherDTO.class);

        template.put("/api/teachers/{id}/courses",
                new CreateCourseCommand("Olasz c1", Languages.ITA),
                teacherCreated.getId());

        template.put("/api/teachers/{id}/courses",
                new CreateCourseCommand("Német c1", Languages.DEU),
                teacherCreated.getId());

        TeacherCourseDTO teacher = template.getForObject("/api/teachers/{id}/courses",
                TeacherCourseDTO.class,
                teacherCreated.getId());

        template.delete("/api/teachers/{id}/courses/{courseId}",
                teacherCreated.getId(),
                teacher.getCourses().get(1).getId());

        System.out.println(teacher.getCourses().get(1).getId());

        TeacherCourseDTO teacherFound = template.getForObject("/api/teachers/{id}/courses",
                TeacherCourseDTO.class,
                teacherCreated.getId());


        assertThat(teacherFound.getCourses())
                .hasSize(1)
                .extracting(CourseListDTO::getName)
                .contains("Olasz c1");

    }


}
