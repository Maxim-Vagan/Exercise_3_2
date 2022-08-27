package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;
import java.util.stream.Collectors;

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
        return facultyRepo.findById(inpId).get();
    }
    // Read
    @Override
    public List<Faculty> getAllFaculties() {
        return facultyRepo.findAll();
    }

    @Override
    public List<Faculty> getFacultiesByColor(String inpColor) {
        return facultyRepo.findAll().stream()
                .filter(e -> e.getColor().equals(inpColor))
                .collect(Collectors.toList());
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
