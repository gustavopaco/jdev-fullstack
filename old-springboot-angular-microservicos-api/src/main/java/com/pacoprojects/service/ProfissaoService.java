package com.pacoprojects.service;

import com.pacoprojects.model.Profissao;
import com.pacoprojects.repository.ProfissaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ProfissaoService {

    private final ProfissaoRepository profissaoRepository;

    @Cacheable(cacheNames = "profissoes.all")
    public ResponseEntity<List<Profissao>> getProfissoes() {
        return ResponseEntity.ok(profissaoRepository.findAll());
    }
}
