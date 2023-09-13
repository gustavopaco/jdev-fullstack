package com.pacoprojects.dto;

import com.pacoprojects.model.Telefone;
import com.pacoprojects.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    private String nome;
    private List<TelefoneDTO> telefoneList = new ArrayList<>();
    private List<EnderecoDTO> enderecoDTOList = new ArrayList<>();
    private boolean dtoEnabled = true;

    public UsuarioDTO(Usuario usuario) {
        this.nome = usuario.getNome();
        usuario.getTelefones().forEach(telefone -> telefoneList.add(new TelefoneDTO(telefone)));
        usuario.getEnderecos().forEach(endereco -> enderecoDTOList.add(new EnderecoDTO(endereco)));
    }
}
