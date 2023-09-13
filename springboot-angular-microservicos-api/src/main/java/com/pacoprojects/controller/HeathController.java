package com.pacoprojects.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "health")
public class HeathController {

    @GetMapping
    public ResponseEntity<?> appHealth() {
        return ResponseEntity.ok("Ok");
    }
}
