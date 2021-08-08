package br.com.webmvnspringbootthymeleaf.controller;

import br.com.webmvnspringbootthymeleaf.model.Endereco;
import br.com.webmvnspringbootthymeleaf.model.Pessoa;
import br.com.webmvnspringbootthymeleaf.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path = "pessoa")
public class PessoaController {

    private final PessoaService pessoaService;

    @Autowired
    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @ModelAttribute(name = "pessoa")
    public Pessoa getPessoa() {
        return new Pessoa();
    }

    @ModelAttribute(name = "pessoas")
    public List<Pessoa> getPessoas() {
        return pessoaService.getPessoas();
    }

    @GetMapping(path = "inicial")
    public String init() {
        return pessoaService.init();
    }

    @PostMapping(path = "cadastrar")
    public String cadastrarPessoa(@Valid Pessoa pessoa, BindingResult bindingResult, RedirectAttributes attributes) {
        return pessoaService.cadastrarPessoa(pessoa, bindingResult, attributes);
    }

    @GetMapping(path = "edicao/{pessoaID}")
    public ModelAndView edicao(@PathVariable(name = "pessoaID") Long pessoaID) {
        return pessoaService.edicao(pessoaID);
    }

    @GetMapping(path = "deletar/{pessoaID}")
    public String deletarPessoa(@PathVariable(name = "pessoaID") Long pessoaID, RedirectAttributes attributes) {
        return pessoaService.deletarPessoa(pessoaID, attributes);
    }

    @GetMapping(path = "findPessoa", params = {"findname","findsexo"})
    public ModelAndView findByParameter(@RequestParam(name = "findname") String findname, @RequestParam(name = "findsexo", required = false) String findsexo) {
        return pessoaService.findByParameter(findname, findsexo);
    }

    @PostMapping(path = "reset")
    public String resetForm() {
        return pessoaService.resetForm();
    }
}
