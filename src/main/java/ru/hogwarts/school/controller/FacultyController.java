package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty inpFaculty) {
        Faculty resultEntity = facultyService.addFaculty(inpFaculty);
        return ResponseEntity.ok(resultEntity);
    }

    @GetMapping("{facultyID}")
    public ResponseEntity<Faculty> getFaculty(@RequestParam Long facultyID) {
        Faculty resultEntity = facultyService.findFaculty(facultyID);
        if (resultEntity != null) {
            return ResponseEntity.ok(resultEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Faculty>> getFaculties() {
        List<Faculty> resultEntity = facultyService.getAllFaculties();
        if (resultEntity != null) {
            return ResponseEntity.ok(resultEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "/GetByColor", params = "colorName")
    public ResponseEntity<List<Faculty>> getFacultiesByColor(@RequestParam String colorName) {
        List<Faculty> resultEntity = facultyService.getFacultiesByColor(colorName);
        return ResponseEntity.ok(resultEntity);
    }

    @GetMapping(path = "/GetByColorOrName", params = {"colorName", "facultyName"})
    public ResponseEntity<List<Faculty>> getFacultiesByColor(@RequestParam String colorName, String facultyName) {
        List<Faculty> resultEntity = facultyService.getFacultiesByColorOrNameIgnoreCase(colorName, facultyName);
        return ResponseEntity.ok(resultEntity);
    }

    @GetMapping(path = "/GetStudentsOfFaculty", params = "facultyID")
    public ResponseEntity<Set<Student>> getFacultiesByColor(@RequestParam Long facultyID) {
        Set<Student> resultEntity = facultyService.getStudentsOfFaculty(facultyID);
        return ResponseEntity.ok(resultEntity);
    }

    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty inpFaculty) {
        Faculty resultEntity = facultyService.updateFaculty(inpFaculty);
        return ResponseEntity.ok(resultEntity);
    }

    @DeleteMapping
    public ResponseEntity deleteFaculty(@RequestParam long facultyID) {
        facultyService.deleteFaculty(facultyID);
        return ResponseEntity.ok().build();
    }
}
