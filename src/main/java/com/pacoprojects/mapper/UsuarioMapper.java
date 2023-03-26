package com.pacoprojects.mapper;

import com.pacoprojects.auth.AuthenticationRequestDto;
import com.pacoprojects.auth.AuthenticationResponseDto;
import com.pacoprojects.dto.UsuarioUpdateDto;
import com.pacoprojects.model.Telefone;
import com.pacoprojects.auth.RegisterDto;
import com.pacoprojects.model.Usuario;
import com.pacoprojects.dto.UsuarioDto;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UsuarioMapper {
    Usuario toEntity(AuthenticationResponseDto authenticationResponseDto);

    AuthenticationResponseDto toDto(Usuario usuario);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Usuario partialUpdate(AuthenticationResponseDto authenticationResponseDto, @MappingTarget Usuario usuario);

    Usuario toEntity1(AuthenticationRequestDto authenticationRequestDto);

    AuthenticationRequestDto toDto1(Usuario usuario);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Usuario partialUpdate1(AuthenticationRequestDto authenticationRequestDto, @MappingTarget Usuario usuario);

    Usuario toEntity2(RegisterDto registerDto);

    RegisterDto toDto2(Usuario usuario);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Usuario partialUpdate2(RegisterDto registerDto, @MappingTarget Usuario usuario);

    Usuario toEntity3(UsuarioDto usuarioDto);

    UsuarioDto toDto3(Usuario usuario);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Usuario partialUpdate3(UsuarioDto usuarioDto, @MappingTarget Usuario usuario);

    @AfterMapping
    default void linkTelefones(@MappingTarget Usuario usuario) {
        usuario.getTelefones().forEach(telefone -> telefone.setUsuario(usuario));
    }

    @AfterMapping
    default void linkEnderecos(@MappingTarget Usuario usuario) {
        usuario.getEnderecos().forEach(endereco -> endereco.setUsuario(usuario));
    }

    default Set<String> telefonesToTelefoneNumeros(Set<Telefone> telefones) {
        return telefones.stream().map(Telefone::getNumero).collect(Collectors.toSet());
    }

    Usuario toEntity4(UsuarioUpdateDto usuarioUpdateDto);

    UsuarioUpdateDto toDto4(Usuario usuario);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Usuario partialUpdate4(UsuarioUpdateDto usuarioUpdateDto, @MappingTarget Usuario usuario);

}
