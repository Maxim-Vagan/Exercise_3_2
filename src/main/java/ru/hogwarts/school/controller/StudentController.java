package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/student")
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
    public ResponseEntity<List<Student>> getStudent(@RequestParam(required = false) Long studentID) {
        List<Student> resultEntity = new ArrayList<Student>();
        if (studentID != null) {
            Student result = studentService.findStudent(studentID);
            if (result != null) {
                resultEntity.add(result);
                return ResponseEntity.ok(resultEntity);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            resultEntity = studentService.getAllStudents();
            return ResponseEntity.ok(resultEntity);
        }
    }

    @GetMapping(path="/GetByAge", params="{age}")
    public ResponseEntity<List<Student>> getStudentsByAge(@RequestParam int age) {
        List<Student> resultEntity = studentService.getStudentsByAge(age);
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
