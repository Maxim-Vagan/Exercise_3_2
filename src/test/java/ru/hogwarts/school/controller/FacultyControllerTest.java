package ru.hogwarts.school.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.impl.AvatarServiceImpl;
import ru.hogwarts.school.service.impl.FacultyServiceImpl;
import ru.hogwarts.school.service.impl.StudentServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class FacultyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepo;

    @MockBean
    private StudentRepository studRepo;

    @MockBean
    private AvatarRepository avatarRepo;

    @SpyBean
    private FacultyServiceImpl facultyService;

    @SpyBean
    private StudentServiceImpl studentService;

    @SpyBean
    private AvatarServiceImpl avatarService;

    @InjectMocks
    private FacultyController facultyController;
    private JSONObject postFaculty = new JSONObject();
    private Faculty dummyFaculty = new Faculty();
    private Student dummyStudent = new Student();

    @BeforeEach
    void setUp() throws JSONException {
        postFaculty.put("facultyid", 0);
        postFaculty.put("name", "Test Faculty");
        postFaculty.put("color", "red");

        dummyFaculty.setFacultyid(5L);
        dummyFaculty.setName("Test Faculty");
        dummyFaculty.setColor("red");

        dummyStudent.setStudentid(100L);
        dummyStudent.setName("student 100");
        dummyStudent.setAge(100);
    }

    @Test
    void createFaculty() throws Exception {
        when(facultyRepo.save(any(Faculty.class))).thenReturn(dummyFaculty);
        when(facultyRepo.findById(any(Long.class))).thenReturn(Optional.of(dummyFaculty));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty") //send
                        .content(postFaculty.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //receive
                .andExpect(jsonPath("$.facultyid").value(5L))
                .andExpect(jsonPath("$.name").value("Test Faculty"))
                .andExpect(jsonPath("$.color").value("red"));
    }

    @Test
    void getFaculty() throws Exception{
        when(facultyRepo.findById(any(Long.class))).thenReturn(Optional.of(dummyFaculty));
        mockMvc.perform(MockMvcRequestBuilders
                .get("/faculty/5")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.facultyid").value(5L))
            .andExpect(jsonPath("$.name").value("Test Faculty"))
            .andExpect(jsonPath("$.color").value("red"));
    }

    @Test
    void getFaculties() throws Exception{
        when(facultyRepo.findAll()).thenReturn(List.of(dummyFaculty));
        mockMvc.perform(MockMvcRequestBuilders
                .get("/faculty")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void getFacultiesByColor() throws Exception{
        when(facultyRepo.findByColor(any(String.class))).thenReturn(List.of(dummyFaculty));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/findByColor?colorName=red")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void getFacultiesByColorOrNameIgnoreCase() throws Exception{
        when(facultyRepo.findFacultiesByColorOrNameIgnoreCase(any(String.class), any(String.class)))
                .thenReturn(List.of(dummyFaculty));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/findByColorOrName?colorName=red&facultyName=F3")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void updateFaculty() throws Exception{
        dummyFaculty.setColor("green");
        postFaculty.put("color", "green");
        when(facultyRepo.save(any(Faculty.class))).thenReturn(dummyFaculty);
        mockMvc.perform(MockMvcRequestBuilders
                    .put("/faculty")
                    .content(postFaculty.toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.facultyid").value(5L))
                .andExpect(jsonPath("$.name").value("Test Faculty"))
                .andExpect(jsonPath("$.color").value("green"));
    }
}