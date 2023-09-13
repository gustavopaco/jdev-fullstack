package com.pacoprojects.mapper;

import com.pacoprojects.dto.TelefoneAddDto;
import com.pacoprojects.dto.TelefoneDto;
import com.pacoprojects.model.Telefone;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TelefoneMapper {
    Telefone toEntity(TelefoneAddDto telefoneAddDto);

    TelefoneAddDto toDto(Telefone telefone);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Telefone partialUpdate(TelefoneAddDto telefoneAddDto, @MappingTarget Telefone telefone);

    Telefone toEntity1(TelefoneDto telefoneDto);

    TelefoneDto toDto1(Telefone telefone);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Telefone partialUpdate(TelefoneDto telefoneDto, @MappingTarget Telefone telefone);
}
