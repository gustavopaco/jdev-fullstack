package br.com.curso.webmvnspringbootmicroservicos.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

import static br.com.curso.webmvnspringbootmicroservicos.model.Constantes.HOST;
import static br.com.curso.webmvnspringbootmicroservicos.model.Constantes.HTMLCONTENT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@Component
public class JavaGenericMail {

    private static final int STARTTLS25PORT = 25;
    private static final int SSLPORT = 465;
    private static final int STARTTLS587PORT = 587;
    private static final int STARTTLS2587PORT = 2587;

    public JavaGenericMail() {
    }

    public void enviarEmail(String FROM, String USERNAME, String PASSWORD,
                            String FROMNAME,List<String> DESTINATARIOS,
                            String ASSUNTO, String CONTEUDO,
                            boolean isHTML, List<String> anexosBase64, String SMTP) throws Exception{

        // Olhar as configuracoes SMTP do seu email
        Properties properties = getGeneralProperties(SMTP);

        Session session = Session.getDefaultInstance(properties);

        StringBuilder destinatarios = new StringBuilder();

        if (DESTINATARIOS.size() > 1) {
            for (int i = 0; i < DESTINATARIOS.size(); i++) {

                destinatarios.append(DESTINATARIOS.get(i));
                if (i < DESTINATARIOS.size() - 1) {
                    destinatarios.append(",");
                }
            }
        } else {
            destinatarios.append(DESTINATARIOS.get(0));
        }

        Address[] toUser = InternetAddress.parse(destinatarios.toString()); // Setando o Email ou a Lista de Emails que sera enviado, SEPARDO POR ","

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM, FROMNAME)); // Email de quem ta enviando + Nome da empresa que ira aparecer na Caixa de Entrada
        message.setRecipients(Message.RecipientType.TO, toUser); // Para quem esta enviando
        message.setSubject(ASSUNTO); // Assunto do email

        /* Parte 1 do e-mail - Descricao do email */
        MimeBodyPart corpoEmail = new MimeBodyPart();

        if (isHTML) {
            corpoEmail.setContent(CONTEUDO, HTMLCONTENT.getValue());
        } else {
            corpoEmail.setText(CONTEUDO);
        }

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(corpoEmail);

        if (!anexosBase64.isEmpty()) {
            String fileBase64;
            String fileMIMEType;
            String fileType;
            for (String itemBase64 : anexosBase64) {

                /* Obtendo fileBase64, MimeType e FileType */
                fileBase64 = itemBase64.substring(itemBase64.indexOf(",")+1);
                byte[] fileBase64InBytes = Base64.getDecoder().decode(fileBase64);
                fileMIMEType = itemBase64.substring(itemBase64.indexOf(":")+1, itemBase64.indexOf(";"));
                fileType = "." + itemBase64.substring(itemBase64.indexOf("/")+1, itemBase64.indexOf(";"));

                /* Parte 2 do e-mail - Sao os anexos em PDF */
                MimeBodyPart anexoEmail = new MimeBodyPart();

                /* Onde eh passado o simulador de PDF, voce passa seu arquivo gravado no Banco de dados
                 *  O arquivo passado pode ser:
                 *  (InputStream, Tipo do arquivo(formato)) ou (byte[], Tipo do arquivo(formato)) ou (String Base64, Tipo do arquivo(formato)) */
//                anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(fileInputStream, "application/pdf")));
                anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(fileBase64InBytes,fileMIMEType)));
                anexoEmail.setFileName("aquivo" + ((int) (Math.random() * 1000) + 1) + fileType);

                /* Parte 3 do e-mail - Juntando corpo do email com o anexo em PDF*/
                multipart.addBodyPart(anexoEmail);
            }
        }
        /* Parte 4 do e-mail - Setando na mensagem o objeto completo com (corpo do email + anexo) */
        message.setContent(multipart);

        try (Transport transport = session.getTransport()) {
            System.out.println("Sending...");

            // Enviar o Email
            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect(HOST.getValue(), USERNAME, PASSWORD);

            // Send the email.
            transport.sendMessage(message, message.getAllRecipients());
            System.out.println("Email sent!");

        } catch (Exception exception) {
            log.error("Erro ao Enviar EMAIL", exception);
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + exception.getMessage());
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Fail to send mail");
        }
    }

    private Properties getGeneralProperties(String tipoSMTP) {

        Properties properties = new Properties();
        if (tipoSMTP.equalsIgnoreCase("gmail")) {
            properties.put("mail.smtp.starttls", "true"); // Autenticacao
            properties.put("mail.smtp.auth", "true"); // Autorizacao
            properties.put("mail.smtp.port", SSLPORT); // Porta do servidor do Google
            properties.put("mail.ssl.trust", "*");
            properties.put("mail.smtp.trust", "*");
            properties.put("mail.smtp.host", "smtp.gmail.com"); // Servidor do Google Gmail
            properties.put("mail.smtp.socketFactory.port", SSLPORT); // Expecifica a porta a ser conectada pelo socket
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // Classe socket de conexao ao SMTP
        } else if (tipoSMTP.equalsIgnoreCase("aws")){
            properties.put("mail.transport.protocol", "smtp");
            properties.put("mail.smtp.port", STARTTLS587PORT);
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.auth", "true");
        }
        return properties;
    }
}
