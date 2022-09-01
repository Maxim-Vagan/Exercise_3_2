package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface FacultyService {
    // Create
    Faculty addFaculty(Faculty inpFaculty);

    // Read
    Faculty findFaculty(long inpId);

    // Read
    List<Faculty> getAllFaculties();

    // Read
    Set<Student> getStudentsOfFaculty(Long facultyID);

    // Read
    List<Faculty> getFacultiesByColor(String inpColor);

    // Read
    List<Faculty> getFacultiesByColorOrNameIgnoreCase(String inpColor, String inpName);

    // Update
    Faculty updateFaculty(Faculty inpUpdatedFaculty);

    // Delete
    void deleteFaculty(long inpId);
}
