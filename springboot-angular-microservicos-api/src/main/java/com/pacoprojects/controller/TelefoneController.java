package com.pacoprojects.controller;

import com.pacoprojects.dto.TelefoneAddDto;
import com.pacoprojects.dto.TelefoneDto;
import com.pacoprojects.service.TelefoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "telefones")
@RequiredArgsConstructor
public class TelefoneController {

    private final TelefoneService telefoneService;

    @PostMapping
    public ResponseEntity<TelefoneDto> addTelefone(@RequestBody TelefoneAddDto telefone, BindingResult bindingResult) {
        return ResponseEntity.ok(telefoneService.addTelefone(telefone, bindingResult));
    }

    @DeleteMapping(path = "{id}")
    public void deleteTelefone(@PathVariable(name = "id") Long id) {
        telefoneService.deleteTelefone(id);
    }
}
