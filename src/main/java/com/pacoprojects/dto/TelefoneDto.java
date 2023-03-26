package com.pacoprojects.dto;

import com.pacoprojects.model.Telefone;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * A DTO for the {@link Telefone} entity
 */
public record TelefoneDto(
        Long id,

        @NotBlank(message = "Telefone obrigatório.")
        String numero

) implements Serializable {
}
