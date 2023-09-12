package com.pacoprojects.dto;

import com.pacoprojects.model.Telefone;
import lombok.Data;

@Data
public class TelefoneDTO {

    private String numero;

    public TelefoneDTO(Telefone telefone) {
        this.numero = telefone.getNumero();
    }
}
