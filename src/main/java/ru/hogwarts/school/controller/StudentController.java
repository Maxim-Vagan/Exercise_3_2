package ru.hogwarts.school.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exceptions.StudentNotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping(path = "/student")
public class StudentController {
    private final StudentService studentService;
    private final AvatarService avatarService;

    public StudentController(StudentService studentService, AvatarService avatarService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student inpStudent) {
        Student resultEntity = studentService.addStudent(inpStudent);
        return ResponseEntity.ok(resultEntity);
    }
    // Добавление Фото студента на диск + в БД в таблицу сущности Avatar
    @PostMapping(path = "{studentID}/setPhoto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadStudentPicture(@PathVariable Long studentID, @RequestBody MultipartFile inpPicture
        ) throws IOException {
        //FileTreatmentException("Something bad has happend via treatment of uploading file")
        if (inpPicture.getSize() > 1024 * 200) {
            return ResponseEntity.badRequest().body("File great than 200 Kb!");
        }
        avatarService.addPicture(studentID, inpPicture);
        return ResponseEntity.ok().body("File was uploaded successfully");
    }

    @GetMapping("{studentID}")
    public ResponseEntity<Student> getStudent(@PathVariable Long studentID) {
        Student resultEntity = studentService.findStudent(studentID);
        if (resultEntity != null) {
            return ResponseEntity.ok(resultEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // Получение Фото студента путём обращения к БД
    @GetMapping("/{studentID}/photoFromDB")
    public ResponseEntity<byte[]> getPictureOfStudentFromDB(@PathVariable Long studentID) {
        Avatar resultEntity = avatarService.getPicture(studentID);
        if (resultEntity == null){
            return ResponseEntity.badRequest().build();
        }
        HttpHeaders headersHTTP = new HttpHeaders();
        headersHTTP.setContentType(MediaType.parseMediaType(resultEntity.getMediaType()));
        headersHTTP.setContentLength(resultEntity.getFileSize());
        return ResponseEntity.status(HttpStatus.OK)
                .headers(headersHTTP)
                .body(resultEntity.getData());
    }
    // Получение Фото студента из Файла на диске
    @GetMapping("/{studentID}/photoFromFileStore")
    public void getPictureOfStudentFromFileStore(@PathVariable Long studentID, HttpServletResponse response) throws IOException {
        Avatar resultEntity = avatarService.getPicture(studentID);
        if (resultEntity == null) {throw new StudentNotFoundException("Студент не найден");
        }
        Path imagePath = Path.of(resultEntity.getFilePath());

        try (InputStream inpStream = Files.newInputStream(imagePath);
             OutputStream outStream = response.getOutputStream();
             ){
            response.setContentType(resultEntity.getMediaType());
            response.setContentLength((int) resultEntity.getFileSize());
            inpStream.transferTo(outStream);
            response.setStatus(201);
        }
    }

    @GetMapping("/{studentID}/faculty")
    public ResponseEntity<Faculty> getFacultyOfStudent(@PathVariable Long studentID) {
        Faculty resultEntity = studentService.getFacultyOfStudent(studentID);
        if (resultEntity != null) {
            return ResponseEntity.ok(resultEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Student>> getStudents() {
        List<Student> resultEntity = studentService.getAllStudents();
        if (resultEntity != null) {
            return ResponseEntity.ok(resultEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "/findByAge", params = "age")
    public ResponseEntity<List<Student>> getStudentsByAge(@RequestParam int age) {
        List<Student> resultEntity = studentService.getStudentsByAge(age);
        return ResponseEntity.ok(resultEntity);
    }

    @GetMapping(path = "/findBetweenAges", params = {"minAge", "maxAge"})
    public ResponseEntity<List<Student>> getStudentsBetweenAge(@RequestParam int minAge, int maxAge) {
        List<Student> resultEntity = studentService.getStudentsBetweenAges(minAge, maxAge);
        return ResponseEntity.ok(resultEntity);
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student inpStudent) {
        Student resultEntity = studentService.findStudent(inpStudent.getStudentid());
        if (resultEntity != null) {
            studentService.updateStudent(inpStudent);
            return ResponseEntity.ok(resultEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteStudent(@RequestParam long studentID) {
        studentService.deleteStudent(studentID);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path="/countOfAllStudents")
    public ResponseEntity<Integer> getAllStudentsOfSchool() {
        Integer studentCount = studentService.getAllStudentsByQuery();
        return ResponseEntity.ok(studentCount);
    }

    @GetMapping(path="/avgAgeOfStudent")
    public ResponseEntity<Float> getAvgAgeByQuery() {
        Float avgAge = studentService.getAvgAgeByQuery();
        return ResponseEntity.ok(avgAge);
    }

    @GetMapping(path="/last5Students")
    public ResponseEntity<List<Student>> getLast5Students() {
        List<Student> lastFiveStudents = studentService.getLast5Students();
        return ResponseEntity.ok(lastFiveStudents);
    }

    @GetMapping(path="/sortedAlphaNamesOfStudent")
    public ResponseEntity<List<String>> getSortedAlphaNamesOfStudents() {
        List<String> studentNames = studentService.getAlphaNamesOfStudentsByStreamAPI();
        return ResponseEntity.ok(studentNames);
    }

    @GetMapping(path="/avgAgeOfStudentStreamAPI")
    public ResponseEntity<Float> getAvgAgeByStreamAPI() {
        Float avgAge = studentService.getAvgAgeByStreamAPI();
        return ResponseEntity.ok(avgAge);
    }

    @GetMapping(path="/ShowAllStudent")
    public ResponseEntity<?> getAllStudentByThread() {
        studentService.getAllStudentByThread();
        return ResponseEntity.ok().build();
    }

    @GetMapping(path="/ShowAllStudentSynchroThreads")
    public ResponseEntity<?> getAllStudentBySynchroThread() {
        studentService.getAllStudentBySynchroThread();
        return ResponseEntity.ok().build();
    }
}
