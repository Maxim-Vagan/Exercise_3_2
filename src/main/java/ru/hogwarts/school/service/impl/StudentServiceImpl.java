package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studRepo;

    public StudentServiceImpl(StudentRepository studRepo) {
        this.studRepo = studRepo;
    }

    // Create
    @Override
    public Student addStudent(Student inpStudent) {
        return studRepo.save(inpStudent);
    }
    // Read
    @Override
    public Student findStudent(long inpId) {
        return studRepo.findById(inpId).get();
    }
    // Read
    @Override
    public List<Student> getAllStudents() {
        return studRepo.findAll();
    }

    @Override
    public List<Student> getStudentsByAge(int inpAge) {
        return studRepo.findAll().stream()
                .filter(e -> e.getAge()==inpAge)
                .collect(Collectors.toList());
    }

    // Update
    @Override
    public Student updateStudent(Student inpUpdatedStudent) {
        return studRepo.save(inpUpdatedStudent);
    }
    // Delete
    @Override
    public void deleteStudent(long inpId) {
        studRepo.deleteById(inpId);
    }
}
