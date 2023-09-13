package com.pacoprojects.service;

import com.pacoprojects.dto.ProfissaoDto;
import com.pacoprojects.mapper.ProfissaoMapper;
import com.pacoprojects.repository.ProfissaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfissaoService {


    private final ProfissaoRepository profissaoRepository;
    private final ProfissaoMapper profissaoMapper;

    public List<ProfissaoDto> getAllProfissoes() {
        return profissaoRepository.findAll().stream().map(profissaoMapper::toDto).collect(Collectors.toList());
    }
}
