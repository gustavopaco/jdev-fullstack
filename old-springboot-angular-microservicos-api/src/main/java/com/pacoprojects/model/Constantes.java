package com.pacoprojects.model;

import lombok.Getter;

@Getter
public enum Constantes {
    AUTHORIZATION("Authorization"),
    TOKEN_PREFIX("Bearer"),
    SECRET("SenhaExtremamenteSecreta"),
    HTMLCONTENT("text/html; charset=utf-8"),
    ANGULARURL("http://angular-sb-microservicos.s3-website-us-east-1.amazonaws.com/resetPassword/"),
    SMTP("aws"),
    FROM("email@gmail.com"),
    FROMNAME("JOverflow"),
    ASSUNTO("Password Recovery"),
    USERNAME("@gmail.com"),
    PASSWORD("password123"),
    HOST("email-smtp.us-east-1.amazonaws.com");
//    HOST("smtp.gmail.com");

    private String value;

    Constantes(String value) {
        this.value = value;
    }
}
