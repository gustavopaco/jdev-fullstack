package br.com.curso.webmvnspringbootmicroservicos.dto;

import br.com.curso.webmvnspringbootmicroservicos.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data @AllArgsConstructor @NoArgsConstructor
public class UsuarioDTO implements Serializable {

    private String nomeUsuario;
    private String loginUsuario;
    private String cpfUsuario;

    public UsuarioDTO(Usuario usuario) {
        this.nomeUsuario = usuario.getNome();
        this.loginUsuario = usuario.getUsername();
        this.cpfUsuario = usuario.getCpf();
    }
}
