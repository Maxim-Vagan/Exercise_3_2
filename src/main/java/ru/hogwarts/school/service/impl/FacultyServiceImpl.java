package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepo;
    private final Logger facultyLogger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    public FacultyServiceImpl(FacultyRepository facultyRepo) {
        this.facultyRepo = facultyRepo;
    }

    // Create
    @Override
    public Faculty addFaculty(Faculty inpFaculty) {
        facultyLogger.debug("Вызван метод addFaculty c inpFaculty = "+inpFaculty);
        return facultyRepo.save(inpFaculty);
    }
    // Read
    @Override
    public Faculty findFaculty(long inpId) {
        facultyLogger.debug("Вызван метод findFaculty c inpId = " + inpId);
        return facultyRepo.findById(inpId).orElse(null);
    }
    // Read
    @Override
    public List<Faculty> getAllFaculties() {
        facultyLogger.debug("Вызван метод getAllFaculties");
        return facultyRepo.findAll();
    }
    // Read
    @Override
    public List<Faculty> getFacultiesByColor(String inpColor) {
        facultyLogger.debug("Вызван метод getFacultiesByColor c inpColor = " + inpColor);
        return facultyRepo.findByColor(inpColor);
    }
    // Read
    @Override
    public List<Faculty> getFacultiesByColorOrNameIgnoreCase(String inpColor, String inpName) {
        facultyLogger.debug("Вызван метод getFacultiesByColorOrNameIgnoreCase c inpColor = " + inpColor+", и inpName = "+inpName);
        return facultyRepo.findFacultiesByColorIgnoreCaseOrNameIgnoreCase(inpColor, inpName);
    }
    // Read
    @Override
    public List<Student> getStudentsOfFaculty(Long inpFacultyID) {
        Faculty currFaculty = findFaculty(inpFacultyID);
        if (currFaculty!=null) {
            facultyLogger.debug("Вызван метод getStudentsOfFaculty c inpFacultyID = " + inpFacultyID);
            return currFaculty.getStudentsOfFaculty();
        } else {
            facultyLogger.warn("Вызван метод getStudentsOfFaculty с inpFacultyID = " + inpFacultyID + ", а результат - пусто");
            return null;
        }
    }
    // Update
    @Override
    public void updateFaculty(Faculty inpUpdatedFaculty) {
        Faculty updatedFaculty = findFaculty(inpUpdatedFaculty.getFacultyid());
        updatedFaculty.setName(inpUpdatedFaculty.getName());
        updatedFaculty.setColor(inpUpdatedFaculty.getColor());
        facultyLogger.debug("Вызван метод updateFaculty c inpUpdatedFaculty = " + inpUpdatedFaculty);
        facultyRepo.save(updatedFaculty);
    }
    // Delete
    @Override
    public void deleteFaculty(long inpId) {
        facultyLogger.debug("Вызван метод deleteFaculty");
        facultyRepo.deleteById(inpId);
    }
    // Read
    @Override
    public String getLongestFacultyName() {
        facultyLogger.debug("Вызван метод getLongestFacultyName");
        return facultyRepo.findAll().stream()
                .map(Faculty::getName)
                .filter(Objects::nonNull)
                .max(Comparator.comparing(String::length))
                .orElse("Нет факультетов с самым длинным именем");
    }
    // Read
    @Override
    public int getFormulaResult() {
        facultyLogger.debug("Вызван метод getFormulaResult");
        return Stream.iterate(1, a -> a++)
                .limit(1_000_000)
                .reduce(0, Integer::sum);
    }
}
