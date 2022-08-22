package ru.hogwarts.school.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final Map<Long, Faculty> facultiesOfHogwarts = new HashMap<>();
    private Long generatorId = 0L;

    // Create
    @Override
    public Faculty addFaculty(Faculty inpFaculty) {
        long idxOfFaculty = 0L;
        if (findFaculty(inpFaculty.getId()) != null) {
            idxOfFaculty = inpFaculty.getId();
        } else {
            generatorId++;
            idxOfFaculty = generatorId;
        }
        facultiesOfHogwarts.put(generatorId, inpFaculty);
        return facultiesOfHogwarts.get(generatorId);
    }
    // Read
    @Override
    public Faculty findFaculty(long inpId) {
        return facultiesOfHogwarts.get(inpId);
    }
    // Read
    @Override
    public List<Faculty> getAllFaculties() {
        return new ArrayList<Faculty>(facultiesOfHogwarts.values());
    }

    @Override
    public List<Faculty> getFacultiesByColor(String inpColor) {
        return facultiesOfHogwarts.values().stream()
                .filter(e -> e.getColor().equals(inpColor))
                .collect(Collectors.toList());
    }

    // Update
    @Override
    public Faculty updateFaculty(long inpId, Faculty inpUpdatedFaculty) {
        facultiesOfHogwarts.put(inpId, inpUpdatedFaculty);
        return facultiesOfHogwarts.get(inpId);
    }
    // Delete
    @Override
    public Faculty deleteFaculty(long inpId) {
        Faculty deletedFaculty = findFaculty(inpId);
        facultiesOfHogwarts.remove(inpId);
        return deletedFaculty;
    }
}
