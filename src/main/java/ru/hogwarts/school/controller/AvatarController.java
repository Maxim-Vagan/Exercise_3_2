package ru.hogwarts.school.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/avatars")
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @GetMapping
    public ResponseEntity<List<Avatar>> showPicturesOfStudentFromDB(
            @RequestParam("page") Integer inpPage, @RequestParam("limit") Integer inpLimit) {
        List<Avatar> resultEntity = avatarService.getAllPicturesWithPagging(inpPage, inpLimit);
        if (resultEntity == null){
            return ResponseEntity.badRequest().build();
        } else { return ResponseEntity.ok(resultEntity); }
    }
}
