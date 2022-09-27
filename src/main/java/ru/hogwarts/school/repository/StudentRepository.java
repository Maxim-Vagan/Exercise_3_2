package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByAge(Integer age);

    List<Student> findStudentsByAgeBetween(int ageFrom, int ageTo);

    @Query(value = "SELECT count(*) as AllStudentsQty FROM student", nativeQuery = true)
    Integer getAllStudentsOfSchool();

    @Query(value = "SELECT avg(age) as AvgAge FROM student", nativeQuery = true)
    Integer getAvgAgeOfStudents();

    @Query(value = "SELECT * FROM student ORDER BY studentid OFFSET (SELECT count(*) FROM student)-5", nativeQuery = true)
    List<Student> getLast5Students();

}
