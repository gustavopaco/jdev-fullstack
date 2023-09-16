package com.pacoprojects.controller;

import com.pacoprojects.model.Profissao;
import com.pacoprojects.service.ProfissaoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "profissao")
public class ProfissaoController {

    private final ProfissaoService profissaoService;

    @GetMapping
    public ResponseEntity<List<Profissao>> getProfissoes() {
        return profissaoService.getProfissoes();
    }
}
