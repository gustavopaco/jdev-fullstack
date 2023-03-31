package com.pacoprojects.auth;

import com.pacoprojects.dto.EnderecoDto;
import com.pacoprojects.dto.TelefoneDto;
import com.pacoprojects.model.Profissao;
import com.pacoprojects.model.Usuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDate;
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
        @NotBlank(message = "Nome obrigatório.")
        String nome,

        String cpf,
        @Valid
        Set<TelefoneDto> telefones,
        @Valid
        Set<EnderecoDto> enderecos,
        Double salario,
        LocalDate dataNascimento,
        @Valid
        Profissao profissao) implements Serializable {
}
