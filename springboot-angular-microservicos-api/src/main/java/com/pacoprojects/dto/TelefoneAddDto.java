package com.pacoprojects.dto;

import com.pacoprojects.model.Usuario;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * A DTO for the {@link com.pacoprojects.model.Telefone} entity
 */
public record TelefoneAddDto(
        Long id,

        @NotBlank(message = "Telefone obrigatório.")
        String numero,

        Usuario usuario

) implements Serializable {
}
