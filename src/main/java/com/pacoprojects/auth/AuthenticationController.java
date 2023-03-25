package com.pacoprojects.auth;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(path = "authenticate")
    public ResponseEntity<AuthenticationResponseDto> authenticate
            (@Valid @RequestBody AuthenticationDto authenticationDto, BindingResult bindingResult, HttpServletResponse response) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationDto, bindingResult, response));
    }

    @PostMapping(path = "register")
    public ResponseEntity<AuthenticationResponseDto> register
            (@Valid @RequestBody RegisterDto registerDto, BindingResult bindingResult, HttpServletResponse response) {
        return ResponseEntity.ok(authenticationService.register(registerDto, bindingResult, response));
    }
}
