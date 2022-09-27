package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studRepo;
    private final Logger studLogger = LoggerFactory.getLogger(StudentServiceImpl.class);
    public StudentServiceImpl(StudentRepository studRepo) {
        this.studRepo = studRepo;
    }

    // Create
    @Override
    public Student addStudent(Student inpStudent) {
        studLogger.debug("Вызван метод addStudent с inpStudent = " + inpStudent.getName());
        return studRepo.save(inpStudent);
    }
    // Read
    @Override
    public Student findStudent(long inpId) {
        studLogger.debug("Вызван метод findStudent с inpId = " + inpId);
        return studRepo.findById(inpId).orElse(null);
    }
    // Read
    @Override
    public List<Student> getAllStudents() {
        studLogger.debug("Вызван метод getAllStudents");
        return studRepo.findAll();
    }
    // Read
    @Override
    public List<Student> getStudentsByAge(int inpAge) {
        studLogger.debug("Вызван метод getStudentsByAge с inpAge = " + inpAge);
        return studRepo.findByAge(inpAge);
    }
    // Read
    @Override
    public List<Student> getStudentsBetweenAges(int minAge, int maxAge) {
        studLogger.debug("Вызван метод getStudentsBetweenAges с minAge = " + minAge + ", maxAge = " + maxAge);
        return studRepo.findStudentsByAgeBetween (minAge, maxAge);
    }
    // Read
    @Override
    public Faculty getFacultyOfStudent(long inpId) {
        studLogger.debug("Вызван метод getFacultyOfStudent с inpId = " + inpId);
        return findStudent(inpId).getFacultyOfStudent();
    }
    // Update
    @Override
    public Student updateStudent(Student inpUpdatedStudent) {
        Student updatedFaculty = findStudent(inpUpdatedStudent.getStudentid());
        updatedFaculty.setName(inpUpdatedStudent.getName());
        updatedFaculty.setAge(inpUpdatedStudent.getAge());
        studLogger.debug("Вызван метод updateStudent с inpUpdatedStudent.getStudentid = " + inpUpdatedStudent.getStudentid());
        return studRepo.save(inpUpdatedStudent);
    }
    // Delete
    @Override
    public void deleteStudent(long inpId) {
        studLogger.debug("Удаление данных УЗ студента с ID = " + inpId);
        studRepo.deleteById(inpId);
    }
    // Read
    @Override
    public Integer getAllStudentsByQuery(){
        studLogger.debug("Вызван метод getAllStudentsByQuery");
        return studRepo.getAllStudentsOfSchool();
    }
    // Read
    @Override
    public Float getAvgAgeByQuery(){
        studLogger.debug("Вызван метод getAvgAgeByQuery");
        return studRepo.getAvgAgeOfStudents();
    }
    // Read
    @Override
    public List<Student> getLast5Students() {
        studLogger.debug("Вызван метод getLast5Students");
        return studRepo.getLast5Students();
    }
    // Read
    @Override
    public List<String> getAlphaNamesOfStudentsByStreamAPI() {
        studLogger.debug("Вызван метод getStreamAlphaStudents");
        return studRepo.findAll().stream()
                .filter(e -> e.getName().startsWith("А"))
                .map(e -> e.getName().toUpperCase())
                .sorted()
                .collect(Collectors.toList());
    }
    // Read
    @Override
    public Float getAvgAgeByStreamAPI(){
        studLogger.debug("Вызван метод getAvgAgeByStreamAPI");
        return /*(float) studRepo.findAll().stream()
                .map(Student::getAge)
                .reduce(0, Integer::sum) / studRepo.findAll().size()*/
                (float) studRepo.findAll().stream().mapToInt(Student::getAge)
                        .average().orElse(0f);
    }
}
