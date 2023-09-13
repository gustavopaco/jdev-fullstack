package com.pacoprojects.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * A DTO for the {@link com.pacoprojects.model.Telefone} entity
 */
public record TelefoneDto(
        Long id,

        @NotBlank(message = "Telefone obrigatório.")
        String numero

) implements Serializable {
}
