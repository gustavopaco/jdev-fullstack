package com.pacoprojects.service;

import com.pacoprojects.dto.UsuarioDto;
import com.pacoprojects.dto.UsuarioUpdateDto;
import com.pacoprojects.mapper.UsuarioMapper;
import com.pacoprojects.model.Usuario;
import com.pacoprojects.repository.UsuarioRepository;
import com.pacoprojects.util.BeanValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final BeanValidator beanValidator;

    @Cacheable(cacheNames = "usuarios")
    public List<UsuarioDto> getAllUsuarios() {
        return usuarioRepository.findAll(Sort.by("id")).stream().map(usuarioMapper::toDto3).toList();
    }

    public UsuarioDto getUsuarioById(Long id) {
        return usuarioMapper.toDto3(usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário nao foi encontrado.");
                }));
    }

    @CacheEvict(cacheNames = {"usuarios"}, allEntries = true)
    public UsuarioUpdateDto updateUsuario(UsuarioUpdateDto usuarioUpdateDto, BindingResult bindingResult) {

        beanValidator.validate(bindingResult);

        Optional<Usuario> optionalUsuario = usuarioRepository.findById(usuarioUpdateDto.id());

        if (optionalUsuario.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário nao foi encontrado.");
        }

        Usuario usuario = usuarioMapper.partialUpdate4(usuarioUpdateDto, optionalUsuario.get());

        return usuarioMapper.toDto4(usuarioRepository.save(usuario));
    }

    @CacheEvict(cacheNames = {"usuarios"}, allEntries = true)
    public void deleteUsuario(Long id) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);

       if (optionalUsuario.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário nao foi encontrado.");
        }

       usuarioRepository.deleteById(id);
    }
}
