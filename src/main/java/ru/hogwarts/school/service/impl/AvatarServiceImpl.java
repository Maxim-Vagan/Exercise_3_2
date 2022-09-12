package ru.hogwarts.school.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {
    @Value("${student.picture.dir.path}")
    private String picturePath;
    private final AvatarRepository avatarRepo;
    private final StudentService studentService;

    public AvatarServiceImpl(AvatarRepository avatarRepo, StudentService studentService) {
        this.avatarRepo = avatarRepo;
        this.studentService = studentService;
    }

    private String getExtensionOfFile(String inpPath){
        if (inpPath.contains(".")){
            return inpPath.substring(inpPath.lastIndexOf("."),inpPath.length());
        } else {
            return "";
        }
    }

    @Override
    public void addPicture(Long studentID, MultipartFile inpPicture) throws IOException {
        Student curStudent = studentService.findStudent(studentID);

        Path imagePath = Path.of(picturePath, curStudent.getName() + getExtensionOfFile(inpPicture.getOriginalFilename()));
        Files.createDirectories(imagePath.getParent());
        Files.deleteIfExists(imagePath);
        // Создание потоков и вызов метода передачи данных по 1-му килобайту
        try (InputStream inpStream = inpPicture.getInputStream();
             OutputStream outStream = Files.newOutputStream(imagePath, CREATE_NEW);
             BufferedInputStream bufInpStream = new BufferedInputStream(inpStream, 1024);
             BufferedOutputStream bufOutStream = new BufferedOutputStream(outStream, 1024);
        ) {
            bufInpStream.transferTo(bufOutStream);
        }

        Avatar curPicture = avatarRepo.findByStudentStudentid(studentID).orElse(new Avatar());
        curPicture.setStudent(curStudent);
        curPicture.setFilePath(imagePath.toString());
        curPicture.setFileSize(inpPicture.getSize());
        curPicture.setMediaType(inpPicture.getContentType());
        curPicture.setData(inpPicture.getBytes());
        avatarRepo.save(curPicture);
    }

    @Override
    public Avatar getPicture(Long studentID) {
        return avatarRepo.findByStudentStudentid(studentID).orElse(null);
    }

    @Override
    public List<Avatar> getAllPicturesWithPagging(Integer page, Integer limit){
        PageRequest pageRequest = PageRequest.of(page - 1, limit);
        return avatarRepo.findAll(pageRequest).getContent();
    }
}
