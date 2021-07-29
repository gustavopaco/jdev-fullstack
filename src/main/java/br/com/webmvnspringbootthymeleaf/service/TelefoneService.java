package br.com.webmvnspringbootthymeleaf.service;

import br.com.webmvnspringbootthymeleaf.model.Pessoa;
import br.com.webmvnspringbootthymeleaf.model.Telefone;
import br.com.webmvnspringbootthymeleaf.repository.PessoaRepository;
import br.com.webmvnspringbootthymeleaf.repository.TelefoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TelefoneService {

    private final TelefoneRepository telefoneRepository;

    private final PessoaRepository pessoaRepository;

    @Autowired
    public TelefoneService(TelefoneRepository telefoneRepository, PessoaRepository pessoaRepository) {
        this.telefoneRepository = telefoneRepository;
        this.pessoaRepository = pessoaRepository;
    }

    public ModelAndView init(Long pessoaID) {
       Optional<Pessoa> pessoa = pessoaRepository.findById(pessoaID);
       if (pessoa.isEmpty()) {
           throw new IllegalStateException("Pessoa nao encontrada, ou removida");
       }
       List<Telefone> telefones = telefoneRepository.findByPessoaID(pessoaID);

        return new ModelAndView("cadastro/telefone")
                .addObject("pessoa", pessoa.get())
                .addObject("telefones",telefones);
    }

    public String cadastrarTelefone(Long pessoaID, Telefone telefone, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            ArrayList<String> msgs = new ArrayList<>();
            bindingResult.getAllErrors().forEach(objectError -> msgs.add(objectError.getDefaultMessage()));

            attributes.addFlashAttribute("msg", msgs);
            attributes.addFlashAttribute("telefone", telefone);
            return "redirect:/telefone/" + pessoaID;
        }

        Optional<Pessoa> pessoa = pessoaRepository.findById(pessoaID);
        if (pessoa.isEmpty()) {
            throw new IllegalStateException("Dono do telefone nao existe");
        }
        telefone.setPessoa(pessoa.get());
        telefoneRepository.save(telefone);
        attributes.addFlashAttribute("msg", "Telefone cadastrado com sucesso");
        return "redirect:/telefone/" + pessoaID;
    }

    public String deletarTelefone(Long telefoneID, RedirectAttributes attributes) {
        Optional<Telefone> telefone = telefoneRepository.findById(telefoneID);
        if (telefone.isEmpty()) {
            throw new IllegalStateException("Telefone nao existe ou ja foi removido");
        }

        telefoneRepository.deleteById(telefoneID);
        attributes.addFlashAttribute("msg", "Telefone deletado com sucesso");
        return "redirect:/telefone/" + telefone.get().getPessoa().getId();
    }
}
