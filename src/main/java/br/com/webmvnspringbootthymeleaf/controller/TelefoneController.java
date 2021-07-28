package br.com.webmvnspringbootthymeleaf.controller;

import br.com.webmvnspringbootthymeleaf.model.Telefone;
import br.com.webmvnspringbootthymeleaf.service.TelefoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "telefone")
public class TelefoneController {

    private TelefoneService telefoneService;

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
    public String cadastrarTelefone(@PathVariable(name = "pessoaID") Long pessoaID, Telefone telefone) {
        return telefoneService.cadastrarTelefone(pessoaID, telefone);
    }
}
