package br.com.webmvnspringbootthymeleaf.service;

import br.com.webmvnspringbootthymeleaf.model.Pessoa;
import br.com.webmvnspringbootthymeleaf.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
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

    public String cadastrarPessoa(Pessoa pessoa, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            ArrayList<String> msgs = new ArrayList<>();

            bindingResult.getAllErrors().forEach(objectError -> msgs.add(objectError.getDefaultMessage()));

            attributes.addFlashAttribute("msg", msgs);
            attributes.addFlashAttribute("pessoa", pessoa);
            return "redirect:/pessoa/inicial";
        }

        if (pessoa.getId() == null) {
            attributes.addFlashAttribute("msg", "Usuario Cadastrado com sucesso!");
        }else {
            attributes.addFlashAttribute("msg", "Usuario Atualizado com sucesso!");
        }

        pessoaRepository.save(pessoa);
        return "redirect:/pessoa/inicial";
    }

    public ModelAndView edicao(Long pessoaID) {
        ModelAndView mav = new ModelAndView("cadastro/cadastropessoa");
        mav.addObject("pessoa", pessoaRepository.findById(pessoaID).orElseThrow(() -> new IllegalStateException("Pessoa nao encontrada")));
        return mav;
    }

    public String deletarPessoa(Long pessoaID, RedirectAttributes attributes) {
        pessoaRepository.findById(pessoaID)
                .ifPresentOrElse(pessoa -> pessoaRepository
                        .deleteById(pessoa.getId()), () -> {
                    throw new IllegalStateException("Pessoa nao encontrada para deletar");
                });
        attributes.addFlashAttribute("msg", "Pessoa Deletada com Sucesso");
        return "redirect:/pessoa/inicial";
    }

    public List<Pessoa> getPessoas() {
        return pessoaRepository.findAll();
    }

    public ModelAndView findByParameter(String findname, String findsexo) {
        ModelAndView mav = new ModelAndView("cadastro/cadastropessoa");
        if (findname.isBlank() && findsexo.isBlank()) {
            mav.addObject("pessoas", pessoaRepository.findAll());
        } else if (!findname.isBlank() && findsexo.isBlank()){
            mav.addObject("pessoas", pessoaRepository.findByName(findname.trim().toLowerCase()));
        } else if (findname.isBlank() && !findsexo.isBlank()) {
            mav.addObject("pessoas", pessoaRepository.findBySexo(findsexo));
        } else {
            mav.addObject("pessoas", pessoaRepository.findByNameSexo(findname.trim().toLowerCase(), findsexo));
        }
        return mav;
    }

    public String resetForm() {
        return "redirect:/pessoa/inicial";
    }
}
