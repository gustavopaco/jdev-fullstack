package com.pacoprojects.controller;

import com.pacoprojects.model.Usuario;
import com.pacoprojects.service.UserRecoveryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@AllArgsConstructor
@RestController
@RequestMapping(path = "recovery")
public class UserRecoveryController {

    private final UserRecoveryService userRecoveryService;

    @PostMapping
    public ResponseEntity<?> recoveryEmail(@RequestBody Usuario usuario, HttpServletRequest request) {
        return userRecoveryService.recoveryEmail(usuario, request);
    }

    @GetMapping
    public ResponseEntity<?> validationToken(HttpServletRequest request) {
        return userRecoveryService.validationToken(request);
    }

    @PutMapping
    public ResponseEntity<?> updatePassword(@RequestBody Usuario usuario, HttpServletRequest request) {
        return userRecoveryService.updatePassword(usuario, request);
    }
}
