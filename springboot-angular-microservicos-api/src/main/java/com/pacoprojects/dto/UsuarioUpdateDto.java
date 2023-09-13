package com.pacoprojects.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pacoprojects.model.Profissao;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * A DTO for the {@link com.pacoprojects.model.Usuario} entity
 */
public record UsuarioUpdateDto(
        Long id,

        @NotBlank(message = "Nome obrigatório.")
        String nome,

        @Past(message = "Data de aniversário deve ser anterior a data atual.")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
        LocalDate dataNascimento,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        BigDecimal salario,

        @Valid
        Set<TelefoneDto> telefones,

        @Valid
        Set<EnderecoDto> enderecos,

        Profissao profissao

) implements Serializable {
}
