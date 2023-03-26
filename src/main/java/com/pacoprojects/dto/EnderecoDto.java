package com.pacoprojects.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * A DTO for the {@link com.pacoprojects.model.Endereco} entity
 */
public record EnderecoDto(Long id, @NotBlank(message = "Cep obrigatório.") String cep,
                          @NotBlank(message = "Rua obrigatório.") String rua,
                          @NotBlank(message = "Número obrigatório.") String numero, String complemento,
                          @NotBlank(message = "Bairro obrigatório.") String bairro,
                          @NotBlank(message = "Cidade obrigatório.") String cidade,
                          @NotBlank(message = "Estado obrigatório.") String estado) implements Serializable {
}
