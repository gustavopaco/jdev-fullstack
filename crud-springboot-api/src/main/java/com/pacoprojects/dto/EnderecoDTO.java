package com.pacoprojects.dto;

import com.pacoprojects.model.Endereco;
import com.pacoprojects.model.EnderecoAPIResponse;
import lombok.Data;

@Data
public class EnderecoDTO {

    private String cep;
    private String rua;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;

    public EnderecoDTO(Endereco endereco) {
        this.cep = endereco.getCep();
        this.rua = endereco.getRua();
        this.complemento = endereco.getComplemento();
        this.bairro = endereco.getBairro();
        this.cidade = endereco.getCidade();
        this.uf = endereco.getUf();
    }

    public EnderecoDTO(EnderecoAPIResponse endereco) {
        this.cep = endereco.getCep();
        this.rua = endereco.getLogradouro();
        this.complemento = endereco.getComplemento();
        this.bairro = endereco.getBairro();
        this.cidade = endereco.getLocalidade();
        this.uf = endereco.getUf();
    }
}
