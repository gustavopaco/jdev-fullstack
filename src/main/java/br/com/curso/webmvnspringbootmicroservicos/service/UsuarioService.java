package br.com.curso.webmvnspringbootmicroservicos.service;

import br.com.curso.webmvnspringbootmicroservicos.dto.UsuarioDTOGET;
import br.com.curso.webmvnspringbootmicroservicos.model.Constantes;
import br.com.curso.webmvnspringbootmicroservicos.model.Usuario;
import br.com.curso.webmvnspringbootmicroservicos.repository.UsuarioRepository;
import br.com.curso.webmvnspringbootmicroservicos.security.JWTAlex.JWTTokenAutenticacaoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static br.com.curso.webmvnspringbootmicroservicos.model.Constantes.AUTHORIZATION;

//@EnableCaching /* IMPORTANT: Notacao pode ser utilizada a nivel de camada Applicacao(*), Controller e ate Service */
//@CrossOrigin /* IMPORTANT: Notacao pode ser utilizada em camada WebSecurity(*), Controller e ate Service */
/*TODO: Implementar consulta do usuario pelo Token(metodo de quebrar token) e nao por usuario vindo do Formulario.
 *  nos metodos PUT, POST, DELETE */
@AllArgsConstructor
@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JWTTokenAutenticacaoService jwtTokenAutenticacaoService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findUsuarioByLogin(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario e/ou senha nao existe");
        }

        return new User(usuario.getUsername(), usuario.getPassword(), usuario.getAuthorities());
    }

    @Cacheable(cacheNames = "usuarios.all", key = "#id") /* Coloca metodo em cache */
    public ResponseEntity<Usuario> getUsuario(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario.get());
    }

    @Cacheable(cacheNames = "usuarios.all") /* Coloca metodo em cache */
    public ResponseEntity<List<Usuario>> getUsuarios() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    /* IMPORTANT: Simulacao de processo lento. Caso o metodo receba paremetros como esse, eh preciso passar algum valor
     *   que sirva como identificador para saber se a chave ja foi consultada antes, utilizei a URI vinda do request. */
    @Cacheable(cacheNames = "usuarios.all", key = "#request.requestURI") /* Coloca metodo em cache */
    public ResponseEntity<List<UsuarioDTOGET>> getUsuarios(HttpServletRequest request) {
        /* Simulando um processo lento...*/
        // Thread.sleep(6000);
        List<UsuarioDTOGET> usuarioDTOs = new ArrayList<>();
        usuarioRepository.findAll().forEach(usuario -> {
            UsuarioDTOGET usuarioDTO = new UsuarioDTOGET(usuario);
            usuarioDTOs.add(usuarioDTO);
        });
        return ResponseEntity.ok(usuarioDTOs);
    }

    @CacheEvict(cacheNames = "usuarios.all", key = "#usuario.id", allEntries = true) /* Remove do cache caso nao seja utilizado ou atualizado */
    public ResponseEntity<?> addUsuario(Usuario usuario, BindingResult bindingResult,
                                        HttpServletRequest request, HttpServletResponse response) {
        List<String> strings = new ArrayList<>();
        if (bindingResult.hasErrors()) {

            bindingResult.getAllErrors().forEach(objectError -> strings.add(objectError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(strings);
        }

        boolean isUserNameTaken = usuarioRepository.existsUsuarioLogin(0L, usuario.getUsername());
        if (isUserNameTaken) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already taken");
        }

        Map<String, Object> objectMap = jwtTokenAutenticacaoService.generateTokenUser(request, usuario);
        String tokenFormatado = (String) objectMap.get("tokenFormatado");
        String token = (String) objectMap.get("token");

        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
        usuario.setJwt(tokenFormatado);

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        response.setHeader(AUTHORIZATION.getValue(), token);

        UsuarioDTOGET usuarioDTOGET = new UsuarioDTOGET();
        BeanUtils.copyProperties(usuarioSalvo,usuarioDTOGET);

        return ResponseEntity.ok(usuarioDTOGET);
    }

    @CacheEvict(cacheNames = "usuarios.all", key = "#usuario.id", allEntries = true) /* Remove do cache caso nao seja utilizado ou atualizado */
    public ResponseEntity<?> updateUsuario(Usuario usuario, BindingResult bindingResult) {
        List<String> strings = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> strings.add(objectError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(strings);
        }

        boolean userNameTaken = usuarioRepository.existsUsuarioLogin(usuario.getId(), usuario.getUsername());
        if (userNameTaken) {
            throw new IllegalStateException("Username ja esta em uso por outro usuario");
        }

        Optional<Usuario> usuarioConsultado = usuarioRepository.findById(usuario.getId());

        if (usuarioConsultado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        BeanUtils.copyProperties(usuario, usuarioConsultado.get(), "id", "password", "jwt");

        if (!usuario.getPassword().equals(usuarioConsultado.get().getPassword())) {
            if (usuario.getPassword() != null && !bCryptPasswordEncoder.matches(usuario.getPassword(), usuarioConsultado.get().getPassword())) {
                usuarioConsultado.get().setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
            }
        }
        Usuario usuarioSalvo = usuarioRepository.save(usuarioConsultado.get());
        UsuarioDTOGET usuarioDTOGET = new UsuarioDTOGET();
        BeanUtils.copyProperties(usuarioSalvo, usuarioDTOGET);
        return ResponseEntity.ok(usuarioDTOGET);
    }

    @CacheEvict(cacheNames = "usuarios.all", key = "#id", allEntries = true) /* Remove do cache caso nao seja utilizado ou atualizado */
    public ResponseEntity<Void> deleteUsuario(Long id) {

        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        usuarioRepository.delete(usuario.get());
        return ResponseEntity.noContent().build();
    }

    @Cacheable(cacheNames = "usuarios.all", key = "#nome")
    public ResponseEntity<List<Usuario>> getUsuarioByName(String nome) {

        if (nome == null || nome.isBlank()) {
            return ResponseEntity.ok(usuarioRepository.findAll());
        } else {
            return ResponseEntity.ok(usuarioRepository.findUsuarioByName(nome.trim().toUpperCase()));
        }
    }
}
