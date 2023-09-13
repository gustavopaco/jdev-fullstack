package com.pacoprojects.service;

import com.pacoprojects.dto.UsuarioDto;
import com.pacoprojects.dto.UsuarioUpdateDto;
import com.pacoprojects.mapper.UsuarioMapper;
import com.pacoprojects.model.Profissao;
import com.pacoprojects.model.Usuario;
import com.pacoprojects.repository.ProfissaoRepository;
import com.pacoprojects.repository.UsuarioRepository;
import com.pacoprojects.util.BeanValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.mapstruct.MappingTarget;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final BeanValidator beanValidator;
    private final ProfissaoRepository profissaoRepository;

    @Cacheable(cacheNames = "usuarios")
    public Page<UsuarioDto> getAllUsuarios() {
        return usuarioRepository
                .findAll(PageRequest
                        .of(0, 5, Sort.by("nome")))
                .map(usuarioMapper::toDto3);
//        return usuarioRepository.findAll(Sort.by("id")).stream().map(usuarioMapper::toDto3).toList();
    }

    public Page<UsuarioDto> getUsuariosPages(String nome, Pageable pageable) {
        return (nome != null && !nome.isEmpty()) ?
                usuarioRepository.findAllByNomeContainsIgnoreCase(nome, pageable).map(usuarioMapper::toDto3)
                : usuarioRepository.findAll(pageable).map(usuarioMapper::toDto3);
//        return usuarioRepository.findAllByNomeContainsIgnoreCase(nome).stream().map(usuarioMapper::toDto3).collect(Collectors.toList());
    }

    public UsuarioDto getUsuarioById(Long id) {
        return usuarioMapper.toDto3(usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não foi encontrado.")));
    }

    @CacheEvict(cacheNames = {"usuarios"}, allEntries = true)
    public UsuarioUpdateDto updateUsuario(UsuarioUpdateDto usuarioUpdateDto, BindingResult bindingResult) {

        beanValidator.validate(bindingResult);

        Optional<Usuario> usuarioConsultado = usuarioRepository.findById(usuarioUpdateDto.id());

        if (usuarioConsultado.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não foi encontrado.");
        }

        try {
            /*Note: Metodo 1 (Full Automatico) - Nesse caso IMPORTA qual tipo de Classe estamos utilizando, precisa ser Profissao.
             *  Para atualizar Profissao @ManyToOne pelo lado do Usuario é preciso que a Classe UsuarioUpdateDto,
             *  utilize no atributo "profissao" a classe Profissao e nao ProfissaoDto ou ProfissaoFullDto
             *  pois ao tentar mapear os dados para dentro do usuarioMapeado ele nao ira saber transformar ProfissaoDto/ProfissaoFullDto em Profissao
             *  depois basta salvar usuarioMapeado ao Banco de Dados*/
            Usuario usuarioMapeado = usuarioMapper.partialUpdate4(usuarioUpdateDto, usuarioConsultado.get());
            return usuarioMapper.toDto4(usuarioRepository.save(usuarioMapeado));

            /*Note: Metodo 2 (Meio Manual) - Nesse caso NAO importa qual tipo de Classe estamos utilizando, Profissao | ProfissaoDTO | ProfissaoFullDto.
             *   Para atualizar Profissao do usuario @ManyToOne pelo lado do Usuario,
             *   vamos ate o metodo partialUpdate4 e descomentamos a linha -> 71 // @Mapping(target = "profissao", ignore = true)
             *   com isso ao mapear os dados para dentro do novo usuarioMapeado ele vai ignorar a propriedade profissao,
             *   Porem, teremos que fazer o update de profissao de modo MANUAL, utilizando o metodo updateProfissao(usuarioMapeado, usuarioUpdateDto),
             *   esse metodo pega o profissao.id do usuarioUpdateDto e pesquisa o objeto por Id no Banco de Dados
             *   entao seta o objeto profissao dentro do usuarioMapeado, que eh retornado e salva o updateUsuarioProfissao agora completo, no Banco de Dados*/
//            Usuario usuarioMapeado = usuarioMapper.partialUpdate4(usuarioUpdateDto, usuarioConsultado.get());
//            Usuario updateUsuarioProfissao = updateProfissao(usuarioMapeado, usuarioUpdateDto);
//            return usuarioMapper.toDto4(usuarioRepository.save(updateUsuarioProfissao));

            /*Note: Metodo 3 (Full Manual) - Nesse metodo NAO importa qual tipo de Classe estamos utilizando, Profissao | ProfissaoDTO | ProfissaoFullDto.
             *  Para isso, mapeamos a profissao do usuarioUpdateDto para um objeto ProfissaoDto
             *  em seguida, limpamos a lista de profissao do usuarioConsultado, caso ja tenha alguma profissao vindo do Banco de Dados
             *  depois, adicionamos a lista profissaoMapeada.getUsuarios().add(o objeto usuarioConsultado)
             *  no proximo passo adicionamos ao objeto usuarioConsultado.get().setProfissao(a nova profissaoMapeada)
             *  por fim basta salvar no banco o novo usuarioRepository.save(usuarioConsultado.get()*/
//            Profissao profissaoMapeada = profissaoMapper.toEntity1(usuarioUpdateDto.profissao());
//            // remova a associação existente
//            if (usuarioConsultado.get().getProfissao() != null) {
//                usuarioConsultado.get().getProfissao().getUsuarios().remove(usuarioConsultado.get());
//            }
//            profissaoMapeada.getUsuarios().add(usuarioConsultado.get());
//            usuarioConsultado.get().setProfissao(profissaoMapeada);
//            Usuario usuario = usuarioRepository.save(usuarioConsultado.get());
//            Usuario u = usuarioMapper.partialUpdate4(usuarioUpdateDto, usuario);
//            Usuario user = usuarioRepository.save(u);
//            return usuarioMapper.toDto4(user);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


    private Usuario updateProfissao(@MappingTarget Usuario usuario, UsuarioUpdateDto usuarioUpdateDto) {
        if (usuarioUpdateDto.profissao() != null) {
            Profissao profissao = profissaoRepository.findById(usuarioUpdateDto.profissao().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Profissão não encontrada"));
            usuario.setProfissao(profissao);
        }
        return usuario;
    }

    @CacheEvict(cacheNames = {"usuarios"}, allEntries = true)
    public void deleteUsuario(Long id) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);

        if (optionalUsuario.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não foi encontrado.");
        }

        usuarioRepository.deleteById(id);
    }
}
