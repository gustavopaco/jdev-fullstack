package com.pacoprojects.email;

import lombok.Builder;

@Builder
public record EmailObject(

        // Destinatario
        String recipient,

        // Assunto do Email
        String subject,

        // Corpo do Email
        String msgBody,

        // Anexo
        String attachment
) {
}
