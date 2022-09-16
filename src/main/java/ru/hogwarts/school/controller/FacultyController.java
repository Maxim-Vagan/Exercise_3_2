package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

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
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long facultyID) {
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

    @GetMapping(path = "/findByColor", params = "colorName")
    public ResponseEntity<List<Faculty>> getFacultiesByColor(@RequestParam String colorName) {
        List<Faculty> resultEntity = facultyService.getFacultiesByColor(colorName);
        return ResponseEntity.ok(resultEntity);
    }

    @GetMapping(path = "/findByColorOrName", params = {"colorName", "facultyName"})
    public ResponseEntity<List<Faculty>> getFacultiesByColor(@RequestParam String colorName, String facultyName) {
        List<Faculty> resultEntity = facultyService.getFacultiesByColorOrNameIgnoreCase(colorName, facultyName);
        return ResponseEntity.ok(resultEntity);
    }

    @GetMapping("/{facultyID}/students")
    public ResponseEntity<List<Student>> GetStudentsOfFaculty(@PathVariable Long facultyID) {
        List<Student> resultEntity = facultyService.getStudentsOfFaculty(facultyID);
        if (resultEntity != null) {
            return ResponseEntity.ok(resultEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
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
