package br.com.webmvnspringbootthymeleaf.service;

import br.com.webmvnspringbootthymeleaf.model.Pessoa;
import br.com.webmvnspringbootthymeleaf.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

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
        if (pessoa.getNome() != null && !pessoa.getNome().isBlank() && pessoa.getSobrenome() != null
                && !pessoa.getSobrenome().isBlank() && pessoa.getDta() != null) {
            pessoaRepository.save(pessoa);
        }
        return "redirect:/pessoa/inicial";
    }

    public ModelAndView edicao(Long pessoaID) {
        ModelAndView mav = new ModelAndView("cadastro/cadastropessoa");
        mav.addObject("usuario", pessoaRepository.findById(pessoaID).orElseThrow(() -> new IllegalStateException("Pessoa nao encontrada")));
        return mav;
    }

    public String deletarPessoa(Long pessoaID) {
        pessoaRepository.findById(pessoaID)
                .ifPresentOrElse(pessoa -> pessoaRepository
                        .deleteById(pessoa.getId()), () -> {
                    throw new IllegalStateException("Pessoa nao encontrada para deletar");
                });
        return "redirect:/pessoa/inicial";
    }

    public List<Pessoa> getPessoas() {
        return pessoaRepository.findAll();
    }

    public ModelAndView findByParameter(String find) {
        ModelAndView mav = new ModelAndView("cadastro/cadastropessoa");
        if (find == null || find.isBlank()){
            mav.addObject("pessoas", pessoaRepository.findAll());
        }else {
            mav.addObject("pessoas", pessoaRepository.findByParameter(find.trim().toLowerCase()));
        }
        return mav;
    }
}
