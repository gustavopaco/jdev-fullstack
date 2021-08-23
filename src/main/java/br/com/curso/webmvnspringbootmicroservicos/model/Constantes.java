package br.com.curso.webmvnspringbootmicroservicos.model;

import lombok.Getter;

@Getter
public enum Constantes {
    AUTHORIZATION("Authorization"),
    TOKEN_PREFIX("Bearer"),
    SECRET("SenhaExtremamenteSecreta");

    private String value;

    Constantes(String value) {
        this.value = value;
    }
}
