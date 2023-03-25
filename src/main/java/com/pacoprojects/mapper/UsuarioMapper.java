package com.pacoprojects.mapper;

import com.pacoprojects.auth.AuthenticationDto;
import com.pacoprojects.auth.AuthenticationResponseDto;
import com.pacoprojects.auth.RegisterDto;
import com.pacoprojects.usuario.Usuario;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UsuarioMapper {
    Usuario toEntity(AuthenticationResponseDto authenticationResponseDto);

    AuthenticationResponseDto toDto(Usuario usuario);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Usuario partialUpdate(AuthenticationResponseDto authenticationResponseDto, @MappingTarget Usuario usuario);

    Usuario toEntity1(AuthenticationDto authenticationDto);

    AuthenticationDto toDto1(Usuario usuario);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Usuario partialUpdate1(AuthenticationDto authenticationDto, @MappingTarget Usuario usuario);

    Usuario toEntity2(RegisterDto registerDto);

    RegisterDto toDto2(Usuario usuario);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Usuario partialUpdate2(RegisterDto registerDto, @MappingTarget Usuario usuario);
}
