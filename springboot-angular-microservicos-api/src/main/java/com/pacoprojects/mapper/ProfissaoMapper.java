package com.pacoprojects.mapper;

import com.pacoprojects.dto.ProfissaoDto;
import com.pacoprojects.model.Profissao;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UsuarioMapper.class})
public interface ProfissaoMapper {

    @AfterMapping
    default void linkUsuarios(@MappingTarget Profissao profissao) {
        profissao.getUsuarios().forEach(usuario -> usuario.setProfissao(profissao));
    }

    Profissao toEntity(ProfissaoDto profissaoDto);

    ProfissaoDto toDto(Profissao profissao);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Profissao partialUpdate(ProfissaoDto profissaoDto, @MappingTarget Profissao profissao);
}
