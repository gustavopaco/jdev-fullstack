package br.com.curso.webmvnspringbootmicroservicos.model;

import lombok.Getter;

@Getter
public enum Constantes {
    AUTHORIZATION("Authorization"),
    TOKEN_PREFIX("Bearer"),
    SECRET("SenhaExtremamenteSecreta"),
    HTMLCONTENT("text/html; charset=utf-8"),
    REMETENTE("JOverflow"),
    ASSUNTO("Password Recovery"),
    ANGULARURL("http://localhost:4200/resetPassword/"),
    EMAIL("Emailxxxxxxxx"),
    PASSWORD("Passwordxxxxxxxx");

    private String value;

    Constantes(String value) {
        this.value = value;
    }
}
