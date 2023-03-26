package com.pacoprojects.auth;

import com.pacoprojects.model.Endereco;
import com.pacoprojects.dto.TelefoneDto;
import com.pacoprojects.model.Usuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link Usuario} entity
 */
public record RegisterDto(
        @NotBlank(message = "Username obrigatório.")
        @Email(message = "Por favor informe um e-mail corretamente.")
        String username,
        @NotBlank(message = "Password obrigatório.")
        @Size(min = 8, message = "Campo de Password deve ser maior que 8 caracteres.")
        String password,
        @Valid
        Set<TelefoneDto> telefones,
        @Valid
        Set<Endereco> enderecos,
        @NotBlank(message = "Nome obrigatório.")
        String nome) implements Serializable {
}
