package br.com.curso.webmvnspringbootmicroservicos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class WebMvnSpringBootMicroservicosApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    void getTokenValue() {
        String token = "Bearer 654564564654564874321231asxd231da56zxc23c1zxc32";

        System.out.println(token.substring(token.indexOf(" ") + 1));
        System.out.println(token.split(" ")[1]);
        System.out.println(token.replace("Bearer ",""));
    }

    @Test
    void addCrypto() {
        String password = "123";
        System.out.println(new BCryptPasswordEncoder().matches(password, "$2a$10$px23nFDTwDkZ6gEsE7lHDOf1cjbECHxOLPQaFlKuiUdMiHsvRZ6mm"));
    }
}
