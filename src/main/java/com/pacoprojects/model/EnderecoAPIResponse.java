package com.pacoprojects.model;

import lombok.Data;

@Data
public class EnderecoAPIResponse {

    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;

}
