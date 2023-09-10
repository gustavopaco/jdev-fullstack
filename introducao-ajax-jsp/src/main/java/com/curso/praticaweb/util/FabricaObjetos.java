package com.curso.praticaweb.util;

import com.curso.praticaweb.models.Usuario;

import java.util.ArrayList;
import java.util.List;

public class FabricaObjetos {

    public static List<Usuario> carregaUsuarios(){
        List<Usuario> usuarios = new ArrayList<>();
        Usuario usuario = new Usuario();
        usuario.setId_usuario(1L);
        usuario.setLogin("admin");
        usuario.setPassword("bla123");
        usuario.setImagem_base64("imagembase64lavamosnos");
        usuarios.add(usuario);

        return usuarios;
    }
}
