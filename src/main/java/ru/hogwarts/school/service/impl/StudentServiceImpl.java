package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final Map<Long, Student> studentsOfHogwarts = new HashMap<>();
    private Long generatorId = 0L;

    // Create
    @Override
    public Student addStudent(Student inpStudent) {
        long idxOfStudent = 0L;
        if (findStudent(inpStudent.getId()) != null) {
            idxOfStudent = inpStudent.getId();
        } else {
            generatorId++;
            idxOfStudent = generatorId;
        }
        studentsOfHogwarts.put(idxOfStudent, inpStudent);
        return studentsOfHogwarts.get(generatorId);
    }
    // Read
    @Override
    public Student findStudent(long inpId) {
        return studentsOfHogwarts.get(inpId);
    }
    // Read
    @Override
    public List<Student> getAllStudents() {
        return new ArrayList<Student>(studentsOfHogwarts.values());
    }

    @Override
    public List<Student> getStudentsByAge(int inpAge) {
        return studentsOfHogwarts.values().stream()
                .filter(e -> e.getAge()==inpAge)
                .collect(Collectors.toList());
    }

    // Update
    @Override
    public Student updateStudent(long inpId, Student inpUpdatedStudent) {
        studentsOfHogwarts.put(inpId, inpUpdatedStudent);
        return studentsOfHogwarts.get(inpId);
    }
    // Delete
    @Override
    public Student deleteStudent(long inpId) {
        Student deletedStudent = findStudent(inpId);
        studentsOfHogwarts.remove(inpId);
        return deletedStudent;
    }
}
