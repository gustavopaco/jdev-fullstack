package com.pacoprojects.controller;

import com.pacoprojects.dto.ProfissaoDto;
import com.pacoprojects.service.ProfissaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "profissoes")
@RequiredArgsConstructor
public class ProfissaoController {

    private final ProfissaoService profissaoService;

    @GetMapping
    public ResponseEntity<List<ProfissaoDto>> getAllProfissoes() {
        return ResponseEntity.ok(profissaoService.getAllProfissoes());
    }
}
