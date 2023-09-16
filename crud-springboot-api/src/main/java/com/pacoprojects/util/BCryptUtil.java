package com.pacoprojects.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BCryptUtil{

    @Bean
    public BCryptPasswordEncoder password() {
        return new BCryptPasswordEncoder();
    }
}
