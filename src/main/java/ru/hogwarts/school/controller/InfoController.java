package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {
    @Value("${server.port}")
    int serverPort;
    private Logger infoLogger = LoggerFactory.getLogger(InfoController.class);

    @GetMapping("/getPort")
    public ResponseEntity<Integer> responseAppPort(){
        infoLogger.info("Порт Веб-приложения = " + serverPort);
        return ResponseEntity.ok(serverPort);
    }
}
