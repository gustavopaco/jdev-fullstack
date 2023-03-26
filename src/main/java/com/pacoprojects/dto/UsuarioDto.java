package com.pacoprojects.dto;

import com.pacoprojects.model.Usuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link Usuario} entity
 */
public record UsuarioDto(
        Long id,

        @NotBlank(message = "Nome obrigatório.")
        String nome,

        @NotBlank(message = "Username obrigatório.")
        @Email(message = "Por favor informe um e-mail corretamente.")
        String username,

        @Valid
        Set<RoleDto> authorities,

        @Valid
        Set<TelefoneDto> telefones,

        @Valid
        Set<EnderecoDto> enderecos,

        boolean enabled

) implements Serializable {
}
