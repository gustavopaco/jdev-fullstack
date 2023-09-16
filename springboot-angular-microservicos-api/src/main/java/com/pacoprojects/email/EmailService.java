package com.pacoprojects.email;

public interface EmailService {

    // Method
    // To send a simple email
    void sendSimpleMail(EmailObject emailObject);

    // Method
    // To send an email with attachment
    void sendMailWithAttachment(EmailObject emailObject);
}
