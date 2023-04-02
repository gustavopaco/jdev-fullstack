package com.pacoprojects.email;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "application.password")
public class EmailConfig {

    private String recoveryUrl;
}
