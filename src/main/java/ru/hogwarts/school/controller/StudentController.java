package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping(path="/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity createStudent(@RequestBody Student inpStudent) {
        Student resultEntity = studentService.addStudent(inpStudent);
        return ResponseEntity.ok(resultEntity);
    }

    @GetMapping
    public ResponseEntity getStudent(@RequestParam(value = "studentID", required = false) Long studentID) {
        if (studentID != null) {
            Student resultEntity = studentService.findStudent(studentID);
            if (resultEntity != null) {
                return ResponseEntity.ok(resultEntity);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            List<Student> resultEntity = studentService.getAllStudents();
            return ResponseEntity.ok(resultEntity);
        }
    }

    @GetMapping(path="/GetByAge")
    public ResponseEntity getStudentsByAge(@RequestParam("age") int studentsAge) {
        List<Student> resultEntity = studentService.getStudentsByAge(studentsAge);
        return ResponseEntity.ok(resultEntity);
    }

    @PutMapping
    public ResponseEntity updateStudent(@RequestBody Student inpStudent) {
        Student resultEntity = studentService.updateStudent(inpStudent.getId(), inpStudent);
        return ResponseEntity.ok(resultEntity);
    }

    @DeleteMapping
    public ResponseEntity deleteStudent(@RequestParam long studentID) {
        Student resultEntity = studentService.deleteStudent(studentID);
        if (resultEntity != null) {
            return ResponseEntity.ok(resultEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
