package com.pacoprojects;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;

@SpringBootTest
class WebMvnSpringBootThymeleafApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void testCrypto() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String codificacao = encoder.encode("admin");
        System.out.println(codificacao);
    }
}
