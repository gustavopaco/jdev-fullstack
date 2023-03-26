package com.pacoprojects.auth;

import com.pacoprojects.dto.RoleDto;
import com.pacoprojects.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link Usuario} entity
 */
public record AuthenticationResponseDto(
        Long id,
        @Email(message = "Por favor informe um e-mail corretamente.")
        @NotBlank(message = "Username obrigatório.")
        String username,
        String jwt,
        Set<RoleDto> authorities
) implements Serializable {
}
