package com.pacoprojects.recovery;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RecoveryRequestNewPasswordDto(
        String basicToken,

        @NotBlank(message = "Password obrigatório.")
        @Size(min = 8, message = "Campo de Password deve ser maior que 8 caracteres.")
        String newPassword) {
}
