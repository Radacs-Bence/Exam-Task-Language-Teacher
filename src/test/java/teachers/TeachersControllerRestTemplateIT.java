package teachers;

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

    @Test
    void listTeachersTest(){
        List<TeacherDTO> teachers = template.exchange("/api/teachers",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TeacherDTO>>() {})
                .getBody();

        assertThat(teachers)
                .hasSize(3)
                .extracting(TeacherDTO::getName)
                .containsExactly("Gipsz Jakab", "Gipsz Jónás", "Kis Piroska");
    }

    @Test
    void findTeacherByIdTest(){
        TeacherDTO teacher = template.exchange("/api/teachers/1",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<TeacherDTO>() {})
                .getBody();

        assertThat(teacher.getName()).isEqualTo("Gipsz Jakab");
    }

    @Test
    void saveTeacherTest(){
        TeacherDTO teacher = template.postForObject("/api/teachers",
                new CreateTeacherCommand("Nagy Piroska"),
                TeacherDTO.class);

        assertThat(teacher.getName()).isEqualTo("Nagy Piroska");

        teachersService.deleteTeacher(teacher.getId());
    }

    @Test
    void addLanguageTest(){
        TeacherDTO teacher = template.postForObject("/api/teachers/1/languages",
                new UpdateLanguageCommand(Languages.ESP),
                TeacherDTO.class);

        assertThat(teacher.getLanguages()).contains(Languages.ESP);
    }

    @Test
    void modifyContactTest(){
        template.put("/api/teachers/1/contact",
                new UpdateContactCommand("Van utca", "blabla@ize.com", "201234567"));

        TeacherDTO teacher = template.exchange("/api/teachers/1",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<TeacherDTO>() {})
                .getBody();

        assertThat(teacher.getContact().getAddress()).isEqualTo("Van utca");
    }

    @Test
    void deleteTeacherTest(){
        TeacherDTO teacher = template.postForObject("/api/teachers",
                new CreateTeacherCommand("Nagy Piroska"),
                TeacherDTO.class);

        assertThat(teacher.getName()).isEqualTo("Nagy Piroska");

        teachersService.deleteTeacher(teacher.getId());

        List<TeacherDTO> teachers = template.exchange("/api/teachers",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TeacherDTO>>() {})
                .getBody();

        assertThat(teachers)
                .hasSize(3)
                .extracting(TeacherDTO::getName)
                .containsExactly("Gipsz Jakab", "Gipsz Jónás", "Kis Piroska");
    }



}
