package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.util.List;

public interface AvatarService {
    //Create
    void addPicture(Long studentID, MultipartFile inpPicture) throws IOException;

    //Read
    Avatar getPicture(Long studentID);

    List<Avatar> getAllPicturesWithPagging(Integer page, Integer limit);
}
