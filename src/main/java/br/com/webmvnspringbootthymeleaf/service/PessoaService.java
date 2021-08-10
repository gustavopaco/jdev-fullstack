package br.com.webmvnspringbootthymeleaf.service;

import br.com.webmvnspringbootthymeleaf.model.Pessoa;
import br.com.webmvnspringbootthymeleaf.model.Profissao;
import br.com.webmvnspringbootthymeleaf.repository.PessoaRepository;
import br.com.webmvnspringbootthymeleaf.repository.ProfissaoRepository;
import br.com.webmvnspringbootthymeleaf.util.RelatorioGenericoPDFUtil;
import br.com.webmvnspringbootthymeleaf.util.RelatorioGeralGenerico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final RelatorioGenericoPDFUtil relatorioGenericoPDFUtil;
    private final RelatorioGeralGenerico relatorioGeralGenerico;
    private final ProfissaoRepository profissaoRepository;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository, RelatorioGenericoPDFUtil relatorioGenericoPDFUtil, RelatorioGeralGenerico relatorioGeralGenerico, ProfissaoRepository profissaoRepository) {
        this.pessoaRepository = pessoaRepository;
        this.relatorioGenericoPDFUtil = relatorioGenericoPDFUtil;
        this.relatorioGeralGenerico = relatorioGeralGenerico;
        this.profissaoRepository = profissaoRepository;
    }

    public String init() {
        return "cadastro/cadastropessoa";
    }

    public String cadastrarPessoa(Pessoa pessoa, MultipartFile file, BindingResult bindingResult, RedirectAttributes attributes) throws Exception {
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

        if(!file.isEmpty()) {
            pessoa.setCurriculo(file.getBytes());
            pessoa.setFileName(file.getOriginalFilename());
            pessoa.setFileContentType(file.getContentType());
        } else if(pessoa.getId() != null) {
            Optional<Pessoa> pessoaTemp = pessoaRepository.findById(pessoa.getId());
            pessoaTemp.ifPresent(pes -> {
                if (pes.getCurriculo() != null) {
                    pessoa.setCurriculo(pes.getCurriculo());
                    pessoa.setFileName(pes.getFileName());
                    pessoa.setFileContentType(pes.getFileContentType());
                }
            });
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
        mav.addObject("pessoas", pesquisaPessoaParametro(findname,findsexo));
        return mav;
    }

    public String resetForm() {
        return "redirect:/pessoa/inicial";
    }

    public void gerarRelatorioFinal(HttpServletRequest request, HttpServletResponse response, String formato, String findname, String findsexo) throws Exception {

        String nomeRelatorio = "relatorio" + ((int)(Math.random()*100000)+1);

        List<Pessoa> pessoas = pesquisaPessoaParametro(findname,findsexo);

        String path = relatorioGeralGenerico.gerarRelatorio(pessoas, new HashMap<>(),"relatorio",formato);
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);

        // Obter o tipo MIME do arquivo
        String mimeType = request.getServletContext().getMimeType(path);
        if (mimeType == null) {
            // Define como tipo binario se mapeamento mime nao for encontrado
            mimeType = "application/octet-stream";
        }

        String type = mimeType.contains("excel") ? ".xls" : ".pdf";

        response.setContentLengthLong(file.length());
        response.setContentType(mimeType);

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename = " + nomeRelatorio + type;

        response.setHeader(headerKey,headerValue);
        response.getOutputStream().write(fileInputStream.readAllBytes());

        response.getOutputStream().close();
        fileInputStream.close();
        if (file.exists()) {
            file.delete();
        }
    }

    public void gerarRelatorio(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Pessoa> pessoas = pessoaRepository.findAll();

        byte[] bytes = relatorioGenericoPDFUtil.gerarRelatorio(pessoas, "pessoa");

        response.setContentLength(bytes.length);

        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename = relatorio" + (Math.random()*10000) + ".pdf";

        response.setHeader(headerKey, headerValue);

        response.getOutputStream().write(bytes);
    }

    private List<Pessoa> pesquisaPessoaParametro(String findname, String findsexo) {
        List<Pessoa> pessoas;

        if (findname.isBlank() && findsexo.isBlank()) {
            pessoas = pessoaRepository.findAll();
        } else if (!findname.isBlank() && findsexo.isBlank()){
            pessoas = pessoaRepository.findByName(findname.trim().toLowerCase());
        } else if (findname.isBlank() && !findsexo.isBlank()) {
            pessoas = pessoaRepository.findBySexo(findsexo);
        } else {
            pessoas = pessoaRepository.findByNameSexo(findname.trim().toLowerCase(), findsexo);
        }
        return pessoas;
    }

    public List<Profissao> getProfissoes() {
        return profissaoRepository.findAll();
    }

    public void downloadCurriculo(Long pessoaID, HttpServletResponse response) throws Exception{
        Pessoa pessoa = pessoaRepository.getById(pessoaID);

        response.setContentType(pessoa.getFileContentType());
        response.setContentLength(pessoa.getCurriculo().length);

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename = " + pessoa.getFileName();

        response.setHeader(headerKey,headerValue);

        response.getOutputStream().write(pessoa.getCurriculo());
        response.getOutputStream().close();
    }
}
