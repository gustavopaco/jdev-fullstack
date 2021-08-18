package br.com.curso.webmvnspringbootmicroservicos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class AddressResponse {

    private String cep;

    private String logradouro;

    private String complemento;

    private String bairro;

    private String localidade;

    private String uf;

    @JsonIgnore
    private boolean erro;
}
