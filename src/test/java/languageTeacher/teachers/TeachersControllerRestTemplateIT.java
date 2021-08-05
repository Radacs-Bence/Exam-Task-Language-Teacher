package languageTeacher.teachers;

import languageTeacher.Languages;
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

    @BeforeEach
    void init() {
        teachersService.saveTeacher(new CreateTeacherCommand("Gipsz Jakab"));
        teachersService.saveTeacher(new CreateTeacherCommand("Gipsz Jónás"));
        teachersService.saveTeacher(new CreateTeacherCommand("Kis Piroska"));
    }

    @AfterEach
    void clean() {
        teachersService.deleteAll();
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

        teachersService.deleteTeacher(teacher.getId());

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


}
