package br.com.webmvnspringbootthymeleaf.service;

import br.com.webmvnspringbootthymeleaf.model.Pessoa;
import br.com.webmvnspringbootthymeleaf.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }


    public String init() {
        return "cadastro/cadastropessoa";
    }

    public String cadastrarPessoa(Pessoa pessoa) {
        if (pessoa.getNome() != null && !pessoa.getNome().isBlank() && pessoa.getSobrenome() != null && !pessoa.getSobrenome().isBlank()) {
            pessoaRepository.save(pessoa);
            return "redirect:/pessoa/inicial";
        }
            return "index";
    }

    public ModelAndView getPessoas() {
        ModelAndView mav = new ModelAndView("cadastro/cadastropessoa");
        mav.addObject("pessoas", pessoaRepository.findAll());
        return mav;
    }
}
