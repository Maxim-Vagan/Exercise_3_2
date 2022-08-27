package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.List;

public interface FacultyService {
    // Create
    Faculty addFaculty(Faculty inpFaculty);

    // Read
    Faculty findFaculty(long inpId);

    // Read
    List<Faculty> getAllFaculties();

    // Read
    List<Faculty> getFacultiesByColor(String inpColor);

    // Update
    Faculty updateFaculty(Faculty inpUpdatedFaculty);

    // Delete
    void deleteFaculty(long inpId);
}
