package com.pacoprojects.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pacoprojects.dto.EnderecoDto;
import com.pacoprojects.dto.TelefoneDto;
import com.pacoprojects.model.Profissao;
import com.pacoprojects.model.Usuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
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

        @CPF(message = "CPF inválido.")
        String cpf,

        @Past(message = "Data de aniversário deve ser anterior a data atual.")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
        LocalDate dataNascimento,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        BigDecimal salario,

        @Valid
        Set<TelefoneDto> telefones,

        @Valid
        Set<EnderecoDto> enderecos,

        @Valid
        Profissao profissao

) implements Serializable {
}
