package com.pacoprojects.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping(path = "/")
    ResponseEntity<?> init() {
        return new ResponseEntity<>("Ola Rest Spring Boot", HttpStatus.OK);
    }
}
