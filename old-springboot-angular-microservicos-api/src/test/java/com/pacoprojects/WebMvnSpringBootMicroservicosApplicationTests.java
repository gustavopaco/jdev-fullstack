package com.pacoprojects;

import com.pacoprojects.model.Usuario;
import com.pacoprojects.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class WebMvnSpringBootMicroservicosApplicationTests {

    @Autowired
    private UsuarioRepository usuarioRepository;

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

    @Test
    void distance() {
        double latitude = -19.960554;
        double longitude = -43.993486;

        System.out.println("Latitude" + latitude);
        System.out.println("Longitude" + longitude);
    }

    @Test
    void testUsuarioSalario() {

        List<Usuario>list = usuarioRepository.findUsuarioBySalarioGreaterThan(new BigDecimal(0));

        List<String> nomes = new ArrayList<>();
        List<BigDecimal> salarios = new ArrayList<>();

        list.forEach(usuario -> {
            nomes.add(usuario.getNome());
            salarios.add(usuario.getSalario());
        });

        System.out.println(nomes);
        System.out.println(salarios);

    }
}
