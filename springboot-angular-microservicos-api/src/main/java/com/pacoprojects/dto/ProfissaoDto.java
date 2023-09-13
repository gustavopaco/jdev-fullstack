package com.pacoprojects.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * A DTO for the {@link com.pacoprojects.model.Profissao} entity
 */
public record ProfissaoDto(
        Long id,

        @NotBlank(message = "Nome da profissão obrigatório")
        String nome

) implements Serializable {
}
