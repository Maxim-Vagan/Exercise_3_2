package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepo;

    public FacultyServiceImpl(FacultyRepository facultyRepo) {
        this.facultyRepo = facultyRepo;
    }

    // Create
    @Override
    public Faculty addFaculty(Faculty inpFaculty) {
        return facultyRepo.save(inpFaculty);
    }
    // Read
    @Override
    public Faculty findFaculty(long inpId) {
        return facultyRepo.findById(inpId).orElse(null);
    }
    // Read
    @Override
    public List<Faculty> getAllFaculties() {
        return facultyRepo.findAll();
    }
    // Read
    @Override
    public List<Faculty> getFacultiesByColor(String inpColor) {
        return facultyRepo.findByColor(inpColor);
    }
    // Read
    @Override
    public List<Faculty> getFacultiesByColorOrNameIgnoreCase(String inpColor, String inpName) {
        return facultyRepo.findFacultiesByColorOrNameIgnoreCase(inpColor, inpName);
    }
    // Read
    @Override
    public Set<Student> getStudentsOfFaculty(Long inpFacultyID) {
        return findFaculty(inpFacultyID).getStudentsOfFaculty();
    }
    // Update
    @Override
    public Faculty updateFaculty(Faculty inpUpdatedFaculty) {
        return facultyRepo.save(inpUpdatedFaculty);
    }
    // Delete
    @Override
    public void deleteFaculty(long inpId) {
        facultyRepo.deleteById(inpId);
    }
}
