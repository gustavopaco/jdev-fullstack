package com.pacoprojects.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pacoprojects.model.Usuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * A DTO for the {@link Usuario} entity
 */
public record UsuarioDto(
        Long id,

        @NotBlank(message = "Username obrigatório.")
        @Email(message = "Por favor informe um e-mail corretamente.")
        String username,

        @NotBlank(message = "Nome obrigatório.")
        String nome,

        @Past(message = "Data de aniversário deve ser anterior a data atual.")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
        LocalDate dataNascimento,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        BigDecimal salario,

        ProfissaoDto profissao,

        @Valid
        Set<RoleDto> authorities,

        @Valid
        Set<TelefoneDto> telefones,

        @Valid
        Set<EnderecoDto> enderecos,

        boolean enabled

) implements Serializable {
}
