package br.com.curso.webmvnspringbootmicroservicos.service;

import br.com.curso.webmvnspringbootmicroservicos.model.Usuario;
import br.com.curso.webmvnspringbootmicroservicos.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@EnableCaching /* IMPORTANT: Notacao pode ser utilizada a nivel de camada Applicacao(*), Controller e ate Service */
//@CrossOrigin /* IMPORTANT: Notacao pode ser utilizada em camada WebSecurity(*), Controller e ate Service */
@AllArgsConstructor
@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findUsuarioByLogin(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario e/ou senha nao existe");
        }

        return new User(usuario.getUsername(), usuario.getPassword(), usuario.getAuthorities());
    }

    public ResponseEntity<Usuario> getUsuario(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario.get());
    }

    public ResponseEntity<List<Usuario>> getUsuarios() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    /* IMPORTANT: Simulacao de processo lento. Caso o metodo receba paremetros como esse, eh preciso passar algum valor
    *   que sirva como identificador para saber se a chave ja foi consultada antes, utilizei a URI vinda do request. */
    @Cacheable(value = "getUsuarios", key = "#request.requestURI")
    public ResponseEntity<List<Usuario>> getUsuarios(HttpServletRequest request) throws InterruptedException {
        System.out.println("Metodo chamado sem cache");
        /* Simulando um processo lento...*/
        Thread.sleep(6000);
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    public ResponseEntity<?> addUsuario(Usuario usuario, BindingResult bindingResult) {
        List<String> strings = new ArrayList<>();
        if (bindingResult.hasErrors()) {

            bindingResult.getAllErrors().forEach(objectError -> strings.add(objectError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(strings);
        }
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
        Usuario u = usuarioRepository.save(usuario);
        return ResponseEntity.ok(u);
    }

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

        BeanUtils.copyProperties(usuario, usuarioConsultado.get(), "id", "password");

        if (!bCryptPasswordEncoder.matches(usuario.getPassword(), usuarioConsultado.get().getPassword())) {
            usuarioConsultado.get().setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
        }
        return ResponseEntity.ok(usuarioRepository.save(usuarioConsultado.get()));
    }

    public ResponseEntity<Void> deleteUsuario(Long id) {

        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        usuarioRepository.delete(usuario.get());
        return ResponseEntity.noContent().build();
    }
}
