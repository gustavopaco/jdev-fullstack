package br.com.curso.webmvnspringbootmicroservicos.util;

import br.com.curso.webmvnspringbootmicroservicos.model.Constantes;

import java.util.ArrayList;
import java.util.List;

import static br.com.curso.webmvnspringbootmicroservicos.model.Constantes.*;

public class TestMail {
    public static void main(String[] args) {
        JavaGenericMail javaGenericMail = new JavaGenericMail();

        List<String> destinatario = new ArrayList<>();
        destinatario.add("gustavopaco@gmail.com");
        String conteudo = "Hello, you are trying to reset your password, if you want to continue, please click on the link below:";

        try {
            javaGenericMail.enviarEmail(FROM.getValue(),
                    USERNAME.getValue(),
                    PASSWORD.getValue(),
                    FROMNAME.getValue(),
                    destinatario,
                    ASSUNTO.getValue(),
                    conteudo,
                    false,
                    new ArrayList<>(),
                    SMTP.getValue());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
