package com.pacoprojects.service;

import com.pacoprojects.model.Pessoa;
import com.pacoprojects.model.Profissao;
import com.pacoprojects.repository.PessoaRepository;
import com.pacoprojects.repository.ProfissaoRepository;
import com.pacoprojects.util.RelatorioGenericoPDFUtil;
import com.pacoprojects.util.RelatorioGeralGenerico;
import com.pacoprojects.util.RelatorioJDEV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final RelatorioGenericoPDFUtil relatorioGenericoPDFUtil;
    private final RelatorioGeralGenerico relatorioGeralGenerico;
    private final RelatorioJDEV relatorioJDEV;
    private final ProfissaoRepository profissaoRepository;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository, RelatorioGenericoPDFUtil relatorioGenericoPDFUtil, RelatorioGeralGenerico relatorioGeralGenerico, RelatorioJDEV relatorioJDEV, ProfissaoRepository profissaoRepository) {
        this.pessoaRepository = pessoaRepository;
        this.relatorioGenericoPDFUtil = relatorioGenericoPDFUtil;
        this.relatorioGeralGenerico = relatorioGeralGenerico;
        this.relatorioJDEV = relatorioJDEV;
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
        } else {
            attributes.addFlashAttribute("msg", "Usuario Atualizado com sucesso!");
        }

        if (!file.isEmpty()) {
            pessoa.setCurriculo(file.getBytes());
            pessoa.setFileName(file.getOriginalFilename());
            pessoa.setFileContentType(file.getContentType());
        } else if (pessoa.getId() != null) {
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

    public Page<Pessoa> getPessoas() {
        return pessoaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome")));
    }

    public ModelAndView findByParameter(String findname, String findsexo, Pageable pageable) {
        ModelAndView mav = new ModelAndView("cadastro/cadastropessoa");
        mav.addObject("pessoas", pesquisaPessoaParametro(findname, findsexo, pageable));
        mav.addObject("findname", findname);
        mav.addObject("findsexo", findsexo);
        return mav;
    }

    public String resetForm() {
        return "redirect:/pessoa/inicial";
    }

    public void gerarRelatorioFinal(HttpServletRequest request, HttpServletResponse response, String formato, String findname, String findsexo) throws Exception {

        String nomeRelatorio = "relatorio" + ((int) (Math.random() * 100000) + 1);

        List<Pessoa> pessoas = getPessoasParaRelatorio(findname, findsexo);

        String path = relatorioGeralGenerico.gerarRelatorio(pessoas, new HashMap<>(), "relatorio", formato);
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

        response.setHeader(headerKey, headerValue);
        response.getOutputStream().write(fileInputStream.readAllBytes());

        response.getOutputStream().close();
        fileInputStream.close();
        if (file.exists()) {
            file.delete();
        }
    }

    private List<Pessoa> getPessoasParaRelatorio(String findname, String findsexo) {
        List<Pessoa> pessoas;
        if (findname.isBlank() && findsexo.isBlank()) {
            pessoas = pessoaRepository.findAll();
        } else if (!findname.isBlank() && findsexo.isBlank()) {
            pessoas = pessoaRepository.findByName(findname.trim().toLowerCase());
        } else if (findname.isBlank() && !findsexo.isBlank()) {
            pessoas = pessoaRepository.findBySexo(findsexo);
        } else {
            pessoas = pessoaRepository.findByNameSexo(findname.trim().toLowerCase(), findsexo);
        }
        return pessoas;
    }

    public void gerarRelatorio(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Pessoa> pessoas = pessoaRepository.findAll();

        byte[] bytes = relatorioGenericoPDFUtil.gerarRelatorio(pessoas, "pessoa");

        response.setContentLength(bytes.length);

        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename = relatorio" + (Math.random() * 10000) + ".pdf";

        response.setHeader(headerKey, headerValue);

        response.getOutputStream().write(bytes);
    }

    private Page<Pessoa> pesquisaPessoaParametro(String findname, String findsexo, Pageable pageable) {
        Page<Pessoa> pessoas;

        if (findname.isBlank() && findsexo.isBlank()) {
            pessoas = pessoaRepository.findAll(pageable);
        } else if (!findname.isBlank() && findsexo.isBlank()) {
            pessoas = pessoaRepository.findPessoaByNamePageQuery(findname.trim().toLowerCase(),pageable);
//            ABAIXO - Outra forma de pesquisa Paginada por Objeto Criteria
//            pessoas = pessoaRepository.findPessoaByNamePage(findname, pageable);
        } else if (findname.isBlank() && !findsexo.isBlank()) {
            pessoas = pessoaRepository.findPessoaBySexoPageQuery(findsexo,pageable);
//            ABAIXO - Outra forma de pesquisa Paginada por Objeto Criteria
//            pessoas = pessoaRepository.findPessoaBySexoPage(findsexo,pageable);
        } else {
            pessoas = pessoaRepository.findPessoaByNameSexoPageQuery(findname.trim().toLowerCase(), findsexo, pageable);
//            ABAIXO - Outra forma de pesquisa Paginada por Objeto Criteria
//            pessoas = pessoaRepository.findPessoaByNameSexoPage(findname.trim(), findsexo, pageable);
        }
        return pessoas;
    }

    public List<Profissao> getProfissoes() {
        return profissaoRepository.findAll();
    }

    public void downloadCurriculo(Long pessoaID, HttpServletResponse response) throws Exception {
        Pessoa pessoa = pessoaRepository.getById(pessoaID);

        response.setContentType(pessoa.getFileContentType());
        response.setContentLength(pessoa.getCurriculo().length);

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename = " + pessoa.getFileName();

        response.setHeader(headerKey, headerValue);

        response.getOutputStream().write(pessoa.getCurriculo());
        response.getOutputStream().close();
    }

    public ModelAndView paginacaoPessoa(Pageable pageable, ModelAndView modelAndView, String findname, String findsexo) {
        Page<Pessoa> pessoaPage = pesquisaPessoaParametro(findname,findsexo,pageable);
        modelAndView.addObject("pessoas", pessoaPage);
        modelAndView.addObject("findname", findname);
        modelAndView.addObject("findsexo", findsexo);
        modelAndView.setViewName("cadastro/cadastropessoa");
        return modelAndView;
    }

    public void gerarRelatorioJDEV(HttpServletRequest request, HttpServletResponse response, String formato, String findname, String findsexo) throws Exception {

        List<Pessoa> pessoas = getPessoasParaRelatorio(findname,findsexo);
        byte[] pdf = relatorioJDEV.gerarRelatorio(pessoas);

        response.setContentLength(pdf.length);
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename = relatorio" + (Math.random() * 10000) + ".pdf";

        response.setHeader(headerKey, headerValue);

        response.getOutputStream().write(pdf);
        response.getOutputStream().close();
    }
}
