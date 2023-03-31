package com.pacoprojects.dto;

import com.pacoprojects.model.Profissao;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * A DTO for the {@link com.pacoprojects.model.Usuario} entity
 */
public record UsuarioUpdateDto(
        Long id,
        @NotBlank(message = "Nome obrigatório.")
        String nome,
        Double salario,
        LocalDate dataNascimento,
        @Valid
        Set<TelefoneDto> telefones,
        @Valid
        Set<EnderecoDto> enderecos,
        Profissao profissao

) implements Serializable {
}
