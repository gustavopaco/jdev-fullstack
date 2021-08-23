package br.com.curso.webmvnspringbootmicroservicos.security.JWTAlex.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
//@EnableWebMvc
public class CorsConfiguration {

    // IMPORTANT: CONFIGURACAO GLOBAL Cors - Aceita requisoes AJAX vindo de IP-URLs diferentes onde API esta hospedada
    //     *** 1/2 ETAPAS ***
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedMethods("GET","POST","PUT","DELETE", "OPTIONS", "HEAD","PATCH","TRACE");
//                .allowedOrigins("Access-Control-Allow-Origin")
//                .allowedHeaders("Origin","Content-Type","Accept","authorization")
//                .exposedHeaders("x-auth-token","Access-Control-Allow-Origin")
//                .allowedOriginPatterns("*");
                    // Mapeamento customizado
                    //  registry.addMapping("/**")
                    // .allowedMethods("GET","POST","PUT","DELETE")
                    // .allowedOrigins("www.servidor1.com","www.empresa1.com");
//    }

    /*@Deprecated
    public void addCorsConfiguration(HttpServletResponse response) {
        if (response.getHeader("Access-Control-Allow-Origin") == null) {
            response.addHeader("Access-Control-Allow-Origin", "*");
        }
        if (response.getHeaders("Access-Control-Allow-Headers") == null) {
            response.addHeader("Access-Control-Allow-Headers", "*");
        }
        if (response.getHeader("Access-Control-Request-Headers") == null) {
            response.addHeader("Access-Control-Request-Headers", "*");
        }
        if (response.getHeader("Access-Control-Max-Age") == null) {
            response.addHeader("Access-Control-Max-Age", "3600");
        }
        if (response.getHeader("Access-Control-Allow-Methods") == null) {
            response.addHeader("Access-Control-Allow-Methods", "*");
        }
    }*/
}

    /*@Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Collections.singletonList("*"));
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }*/

// IMPORTANT: Metodo de permissao AJAX Cors - createdBy Alex
 /*@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        org.springframework.web.cors.CorsConfiguration configuration = new org.springframework.web.cors.CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }*/
