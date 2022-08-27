package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentService {
    // Create
    Student addStudent(Student inpStudent);

    // Read
    Student findStudent(long inpId);

    // Read
    List<Student> getAllStudents();

    // Read
    List<Student> getStudentsByAge(int inpAge);

    // Update
    Student updateStudent(Student inpUpdatedStudent);

    // Delete
    void deleteStudent(long inpId);
}
