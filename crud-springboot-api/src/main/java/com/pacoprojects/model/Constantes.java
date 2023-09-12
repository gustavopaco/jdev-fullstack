package com.pacoprojects.model;

import lombok.Getter;

@Getter
public enum Constantes {

    /* Uma senha unica para compor o JWT na hora de ser gerado */
    SECRET("SenhaExtremamenteSecreta"),
    /* Prefixo padrao para o JWT */
    TOKEN_PREFIX("Bearer");

    private final String value;

    Constantes(String value) {
        this.value = value;
    }
}
