package com.pacoprojects.recovery;

import jakarta.validation.constraints.Email;

public record RecoveryRequestEmailDto(
        @Email(message = "Por favor informe um e-mail corretamente.")
        String email
) {
}
