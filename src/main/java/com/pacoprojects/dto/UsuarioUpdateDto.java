package com.pacoprojects.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link com.pacoprojects.model.Usuario} entity
 */
public record UsuarioUpdateDto(
        Long id,

        @NotBlank(message = "Nome obrigatório.") String nome,

        @Valid
        Set<TelefoneDto> telefones,

        @Valid
        Set<EnderecoDto> enderecos
) implements Serializable {
}
