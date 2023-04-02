package com.pacoprojects.recovery;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "recovery")
@RequiredArgsConstructor
public class RecoveryController {

    private final RecoveryService recoveryService;

    @PostMapping
    public void sendEmailRecoveryPassword(@Valid @RequestBody RecoveryRequestEmailDto requestEmailDto) {
       recoveryService.sendEmailRecoveryPassword(requestEmailDto);
    }

    @PostMapping(path = "validateTokenExpired")
    public ResponseEntity<Boolean> validateBasicTokenExpired(@RequestBody RecoveryRequestValidateToken requestValidateToken) {
        return ResponseEntity.ok(recoveryService.validateBasicTokenExpired(requestValidateToken));
    }

    @PostMapping(path = "resetPassword")
    public void resetPassword(@RequestBody RecoveryRequestNewPasswordDto requestNewPasswordDto) {
        recoveryService.resetPassword(requestNewPasswordDto);
    }

}
