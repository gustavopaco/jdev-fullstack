package com.pacoprojects.dto;

import com.pacoprojects.model.Role;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * A DTO for the {@link Role} entity
 */
public record RoleDto(
        Long id,

        @NotBlank(message = "Permissão obrigatório.")
        String authority

) implements Serializable {
}
