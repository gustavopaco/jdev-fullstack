package br.com.webmvnspringbootthymeleaf;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class WebMvnSpringBootThymeleafApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void testCrypto() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String codificacao = encoder.encode("123");
        System.out.println(codificacao);
    }
}
