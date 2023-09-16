package com.pacoprojects.util;

import com.pacoprojects.model.Constantes;

import java.util.ArrayList;
import java.util.List;

public class TestMail {
    public static void main(String[] args) {
        JavaGenericMail javaGenericMail = new JavaGenericMail();

        List<String> destinatario = new ArrayList<>();
        destinatario.add("gustavopaco@gmail.com");
        String conteudo = "Hello, you are trying to reset your password, if you want to continue, please click on the link below:";

        try {
            javaGenericMail.enviarEmail(Constantes.FROM.getValue(),
                    Constantes.USERNAME.getValue(),
                    Constantes.PASSWORD.getValue(),
                    Constantes.FROMNAME.getValue(),
                    destinatario,
                    Constantes.ASSUNTO.getValue(),
                    conteudo,
                    false,
                    new ArrayList<>(),
                    Constantes.SMTP.getValue());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
