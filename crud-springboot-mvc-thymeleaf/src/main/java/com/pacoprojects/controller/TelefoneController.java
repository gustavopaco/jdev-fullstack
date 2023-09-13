package com.pacoprojects.controller;

import com.pacoprojects.model.Telefone;
import com.pacoprojects.service.TelefoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "telefone")
public class TelefoneController {

    private final TelefoneService telefoneService;

    @Autowired
    public TelefoneController(TelefoneService telefoneService) {
        this.telefoneService = telefoneService;
    }

    @ModelAttribute(name = "telefone")
    public Telefone getNewTelefone() {
        return new Telefone();
    }

    @GetMapping(path = "{pessoaID}")
    public ModelAndView init(@PathVariable(name = "pessoaID") Long pessoaID) {
        return telefoneService.init(pessoaID);
    }

    @PostMapping(path = "cadastrar/{pessoaID}")
    public String cadastrarTelefone(@PathVariable(name = "pessoaID") Long pessoaID, @Valid Telefone telefone, BindingResult bindingResult, RedirectAttributes attributes) {
        return telefoneService.cadastrarTelefone(pessoaID, telefone, bindingResult, attributes);
    }

    @GetMapping(path = "deletar/{telefoneID}")
    public String deletarTelefone(@PathVariable(name = "telefoneID") Long telefoneID, RedirectAttributes attributes) {
        return telefoneService.deletarTelefone(telefoneID, attributes);
    }
}
