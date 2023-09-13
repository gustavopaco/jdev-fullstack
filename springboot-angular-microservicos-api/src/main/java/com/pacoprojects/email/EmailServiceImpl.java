package com.pacoprojects.email;

import com.pacoprojects.util.Constantes;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender javaMailSender;

    @Value(value = "${spring.mail.username}")
    private String sender;

    @Override
    @Async
    public void sendSimpleMail(EmailObject emailObject) {

        // Try block to check for exceptions
        try {

            // Creating a simple mail message
            SimpleMailMessage mailMessage = new SimpleMailMessage();


            // Setting up necessary details
            // De:
            mailMessage.setFrom(sender);

            // Destinatario
            mailMessage.setTo(emailObject.recipient());

            // Assunto do Email
            mailMessage.setSubject(emailObject.subject());

            // Corpo do Email
            mailMessage.setText(emailObject.msgBody());

            // Sending the mail
            javaMailSender.send(mailMessage);

            // Metodo deve ser VOID para conseguir funcionar de modo Asyncrono
            // return "Mail Sent Successfully...";
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constantes.ERROR_WHILE_SENDING_MAIL);
        }

    }

    @Override
    @Async
    public void sendMailWithAttachment(EmailObject emailObject) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;

        try {

            // Setting multipart as true for attachments to be send
            helper = new MimeMessageHelper(mimeMessage, true);


            // Setting up necessary details
            // De:
            helper.setFrom(sender);

            // Destinatario
            helper.setTo(emailObject.recipient());

            // Assunto do Email
            helper.setSubject(emailObject.subject());

            // Corpo do Email
            helper.setText(emailObject.msgBody(), true);

            // Adding the attachment
            if (emailObject.attachment() != null && !emailObject.attachment().isBlank()) {
                FileSystemResource file = new FileSystemResource(emailObject.attachment());
                helper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
            }

            // Sending the mail
            javaMailSender.send(mimeMessage);

            // Metodo deve ser VOID para conseguir funcionar de modo Asyncrono
            // return "Mail Sent Successfully...";

        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constantes.ERROR_WHILE_SENDING_MAIL);
        }
    }
}
