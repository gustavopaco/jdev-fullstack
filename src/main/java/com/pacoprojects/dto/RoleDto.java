package com.pacoprojects.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * A DTO for the {@link com.pacoprojects.role.Role} entity
 */
public record RoleDto(
        Long id,
        @NotBlank(message = "Permissão obrigatório.") String authority
) implements Serializable {
}
