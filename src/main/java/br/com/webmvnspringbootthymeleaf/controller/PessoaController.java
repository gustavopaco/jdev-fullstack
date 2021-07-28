package br.com.webmvnspringbootthymeleaf.controller;

import br.com.webmvnspringbootthymeleaf.model.Pessoa;
import br.com.webmvnspringbootthymeleaf.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "pessoa")
public class PessoaController {

    private final PessoaService pessoaService;

    @Autowired
    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @ModelAttribute(name = "usuario")
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
    public String cadastrarPessoa(Pessoa pessoa) {
        return pessoaService.cadastrarPessoa(pessoa);
    }

    @GetMapping(path = "edicao/{pessoaID}")
    public ModelAndView edicao(@PathVariable(name = "pessoaID") Long pessoaID) {
        return pessoaService.edicao(pessoaID);
    }

    @GetMapping(path = "deletar/{pessoaID}")
    public String deletarPessoa(@PathVariable(name = "pessoaID") Long pessoaID) {
        return pessoaService.deletarPessoa(pessoaID);
    }

    @GetMapping(path = "findPessoa", params = "find")
    public ModelAndView findByParameter(@RequestParam(name = "find") String find) {
        return pessoaService.findByParameter(find);
    }
}
