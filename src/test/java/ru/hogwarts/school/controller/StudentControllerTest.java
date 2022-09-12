package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.model.Student;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @LocalServerPort
    public int locPort;

    @Autowired
    private StudentController studControl;

    @Autowired
    private TestRestTemplate testRestTemp;

    private final Student dummyTestStudent = new Student();

    @Test
    void runControllerTest(){
        Assertions.assertNotNull(studControl);
    }

    @BeforeEach
    void setUp() {
        dummyTestStudent.setStudentid(0L);
        dummyTestStudent.setAge(20);
        dummyTestStudent.setName("студент 7");
    }

    @Test
    void createStudentTest() throws Exception{
        Assertions.assertNotNull(testRestTemp.postForObject("http://localhost:" + locPort + "/hogwarts/student"
                ,dummyTestStudent
                ,String.class)
        );
    }

    @Test
    void getStudentTest() throws Exception{
        String expectedRespondString = "{\"studentid\":3,\"name\":\"студентик 3\",\"age\":20}";
        Assertions.assertEquals(expectedRespondString,
                testRestTemp.getForObject("http://localhost:" + locPort + "/hogwarts/student/3", String.class)
        );
    }

    @Test
    void getFacultyOfStudentTest() throws Exception {
        String expectedRespondString = "{\"facultyid\":1,\"name\":\"факультет 1\",\"color\":\"красный\"}";
        Assertions.assertEquals(expectedRespondString,
                testRestTemp.getForObject("http://localhost:" + locPort + "/hogwarts/student/6/faculty", String.class)
        );
    }

    @Test
    void getStudentsTest() throws Exception {
        String actualRespondString = testRestTemp.getForObject("http://localhost:" + locPort + "/hogwarts/student", String.class);
        Assertions.assertNotNull(actualRespondString);
    }

    @Test
    void getStudentsByAgeTest() throws Exception{
        String expectedRespondString = "[{\"studentid\":6,\"name\":\"студент 6\",\"age\":36}]";
        Assertions.assertEquals(expectedRespondString,
                testRestTemp.getForObject("http://localhost:" + locPort + "/hogwarts/student/findByAge?age=36", String.class)
        );
    }

    @Test
    void getStudentsBetweenAgeTest() throws Exception{
        String expectedRespondString = "[{\"studentid\":6,\"name\":\"студент 6\",\"age\":36}]";
        Assertions.assertEquals(expectedRespondString,
                testRestTemp.getForObject("http://localhost:" + locPort + "/hogwarts/student/findBetweenAges?minAge=30&maxAge=37", String.class)
        );
    }

    @Test
    void updateStudentTest() throws Exception{
        String expectedRespondString = "{\"studentid\":4,\"name\":\"Новый студент 4\",\"age\":50}";
        dummyTestStudent.setStudentid(4L);
        dummyTestStudent.setAge(50);
        dummyTestStudent.setName("Новый студент 4");
        testRestTemp.put("http://localhost:" + locPort + "/hogwarts/student", dummyTestStudent);
        Assertions.assertEquals(expectedRespondString,
                testRestTemp.getForObject("http://localhost:" + locPort + "/hogwarts/student/4", String.class)
        );
    }

    @Test
    void deleteStudentTest() throws Exception{
        //String expectedRespondString = "{\"studentid\":4,\"name\":\"Новый студент 4\",\"age\":50}";
        testRestTemp.delete("http://localhost:" + locPort + "/hogwarts/student?studentID=8");
        Assertions.assertNull(testRestTemp.getForObject("http://localhost:" + locPort + "/hogwarts/student/8", String.class)
        );
    }
}