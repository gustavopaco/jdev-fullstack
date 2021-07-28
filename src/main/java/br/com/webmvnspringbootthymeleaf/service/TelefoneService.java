package br.com.webmvnspringbootthymeleaf.service;

import br.com.webmvnspringbootthymeleaf.model.Pessoa;
import br.com.webmvnspringbootthymeleaf.model.Telefone;
import br.com.webmvnspringbootthymeleaf.repository.PessoaRepository;
import br.com.webmvnspringbootthymeleaf.repository.TelefoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Service
public class TelefoneService {

    private TelefoneRepository telefoneRepository;

    private PessoaRepository pessoaRepository;

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
                .addObject("usuario", pessoa.get())
                .addObject("telefones",telefones);
    }

    public String cadastrarTelefone(Long pessoaID, Telefone telefone) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(pessoaID);
        if (pessoa.isEmpty()) {
            throw new IllegalStateException("Dono do telefone nao existe");
        }
        telefone.setPessoa(pessoa.get());
        telefoneRepository.save(telefone);
        return "redirect:/telefone/" + pessoaID;
    }
}
