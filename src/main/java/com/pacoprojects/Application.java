package com.pacoprojects;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Application implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /* Mapeamento de Cors Global - Necessario implementar a classe WebMvcConfigurer */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/usuario/**") /* Liberando Cors somente de UsuarioController */
                .allowedMethods("*") /* Liberando Cors para qualquer metodo GET,POST,PUT,DELETE */
                .allowedOrigins("*"); /* Liberando Cors de qualquer navegador */
//                .allowedMethods("POST","DELETE");
//                .allowedOrigins("www.cliente40.com", "www.cliente80.com", "localhost:8080");
        /* Liberando apenas requisicoes POST e DELETE do Controller UsuarioController para o site www.cliente40.com e www.cliente80.com e localhost:8080*/
    }
}
