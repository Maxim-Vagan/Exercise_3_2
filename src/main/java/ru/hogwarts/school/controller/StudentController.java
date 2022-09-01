package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping(path = "/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student inpStudent) {
        Student resultEntity = studentService.addStudent(inpStudent);
        return ResponseEntity.ok(resultEntity);
    }

    @GetMapping("{studentID}")
    public ResponseEntity<Student> getStudent(@RequestParam Long studentID) {
        Student resultEntity = studentService.findStudent(studentID);
        if (resultEntity != null) {
            return ResponseEntity.ok(resultEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path="/getFacultyOfStudent", params="studentID")
    public ResponseEntity<Faculty> getFacultyOfStudent(@RequestParam Long studentID) {
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

    @GetMapping(path = "/GetByAge", params = "age")
    public ResponseEntity<List<Student>> getStudentsByAge(@RequestParam int age) {
        List<Student> resultEntity = studentService.getStudentsByAge(age);
        return ResponseEntity.ok(resultEntity);
    }

    @GetMapping(path = "/GetBetweenAges", params = {"minAge", "maxAge"})
    public ResponseEntity<List<Student>> getStudentsBetweenAge(@RequestParam int minAge, int maxAge) {
        List<Student> resultEntity = studentService.getStudentsBetweenAges(minAge, maxAge);
        return ResponseEntity.ok(resultEntity);
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student inpStudent) {
        Student resultEntity = studentService.updateStudent(inpStudent);
        return ResponseEntity.ok(resultEntity);
    }

    @DeleteMapping
    public ResponseEntity deleteStudent(@RequestParam long studentID) {
        studentService.deleteStudent(studentID);
        return ResponseEntity.ok().build();
    }
}
