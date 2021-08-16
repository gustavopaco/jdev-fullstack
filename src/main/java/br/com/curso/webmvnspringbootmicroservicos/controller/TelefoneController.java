package br.com.curso.webmvnspringbootmicroservicos.controller;

import br.com.curso.webmvnspringbootmicroservicos.model.Telefone;
import br.com.curso.webmvnspringbootmicroservicos.service.TelefoneService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping(path = "telefone")
public class TelefoneController {

    private final TelefoneService telefoneService;

    @GetMapping
    ResponseEntity<?> getTelefones(@RequestParam Long usuarioID) {
        return telefoneService.getTelefones(usuarioID);
    }

    @GetMapping(path = "{telefoneID}")
    ResponseEntity<?> getTelefone(@PathVariable Long telefoneID) {
        return telefoneService.getTelefone(telefoneID);
    }

    @PostMapping
    ResponseEntity<?> addTelefone(@RequestParam Long usuarioID, @Valid @RequestBody Telefone telefone, BindingResult bindingResult) {
        return telefoneService.addTelefone(usuarioID, telefone, bindingResult);
    }

    @PutMapping
    ResponseEntity<?> updateTelefone(@Valid @RequestBody Telefone telefone, BindingResult bindingResult) {
        return telefoneService.updateTelefone(telefone,bindingResult);
    }

    @DeleteMapping(path = "{telefoneID}")
    ResponseEntity<Void> deleteTelefone(@PathVariable Long telefoneID) {
        return telefoneService.deleteTelefone(telefoneID);
    }
}
