package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping(path="/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity createStudent(@RequestBody Faculty inpFaculty) {
        Faculty resultEntity = facultyService.addFaculty(inpFaculty);
        return ResponseEntity.ok(resultEntity);
    }

    @GetMapping
    public ResponseEntity getStudent(@RequestParam(value = "facultyID", required = false) Long facultyID) {
        if (facultyID != null) {
            Faculty resultEntity = facultyService.findFaculty(facultyID);
            if (resultEntity != null) {
                return ResponseEntity.ok(resultEntity);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            List<Faculty> resultEntity = facultyService.getAllFaculties();
            return ResponseEntity.ok(resultEntity);
        }
    }
    @GetMapping(path="/GetByColor")
    public ResponseEntity getFacultiesByColor(@RequestParam("color") String facultyColor) {
        List<Faculty> resultEntity = facultyService.getFacultiesByColor(facultyColor);
        return ResponseEntity.ok(resultEntity);
    }
    @PutMapping
    public ResponseEntity updateStudent(@RequestBody Faculty inpFaculty) {
        Faculty resultEntity = facultyService.updateFaculty(inpFaculty.getId(), inpFaculty);
        return ResponseEntity.ok(resultEntity);
    }

    @DeleteMapping
    public ResponseEntity deleteStudent(@RequestParam long facultyID) {
        Faculty resultEntity = facultyService.deleteFaculty(facultyID);
        if (resultEntity != null) {
            return ResponseEntity.ok(resultEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
