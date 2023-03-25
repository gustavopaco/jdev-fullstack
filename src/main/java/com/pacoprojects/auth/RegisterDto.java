package com.pacoprojects.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * A DTO for the {@link com.pacoprojects.usuario.Usuario} entity
 */
public record RegisterDto(
        @Email(message = "Por favor informe um e-mail corretamente.")
        @NotBlank(message = "Username obrigatório.")
        String username,
        @NotBlank(message = "Password obrigatório.")
        @Size(min = 8, message = "Campo de Password deve ser maior que 8 caracteres.")
        String password
) implements Serializable {
}
