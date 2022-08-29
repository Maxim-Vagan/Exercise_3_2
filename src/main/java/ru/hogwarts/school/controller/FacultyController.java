package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/faculty")
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
    public ResponseEntity<List<Faculty>> getFaculty(@RequestParam(required = false) Long facultyID) {
        List<Faculty> resultEntity = new ArrayList<Faculty>();
        if (facultyID != null) {
            Faculty result = facultyService.findFaculty(facultyID);
            if (result != null) {
                resultEntity.add(result);
                return ResponseEntity.ok(resultEntity);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            resultEntity = facultyService.getAllFaculties();
            return ResponseEntity.ok(resultEntity);
        }
    }

    @GetMapping(path="/GetByColor", params="{color}")
    public ResponseEntity<List<Faculty>> getFacultiesByColor(@RequestParam String facultyColor) {
        List<Faculty> resultEntity = facultyService.getFacultiesByColor(facultyColor);
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
