package controller;

import dao.GenericDao;
import dataTableLazy.LazyDataTableModelPessoa;
import model.CartaoCredito;
import model.Pessoa;
import org.primefaces.PrimeFaces;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.context.PrimeFacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.*;
import org.primefaces.util.LangUtils;
import repository.IDaoPessoa;
import service.IServiceCall;
import util.BuildMiniatureImage;
import util.GlobalMessage;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

@ViewScoped
@Named
public class RegisterBean implements Serializable {

    @Inject
    private GenericDao genericDao;

    @Inject
    private IDaoPessoa iDaoPessoa;

    @Inject
    private IServiceCall iServiceCall;

    @Inject
    private LazyDataTableModelPessoa lazyDataTableModelPessoa;

    private LazyDataModel<Pessoa> pessoas;

    private Pessoa pessoa = new Pessoa();
    private CartaoCredito cartaoCredito = new CartaoCredito();
    private Pessoa pessoaEscolhida = new Pessoa();
    private List<Pessoa> filterPessoa = new ArrayList<>();
    private CommandButton btnRegistrar = new CommandButton();
    private CommandButton btnLimpar = new CommandButton();
    private CommandButton btnEditar = new CommandButton();
    private CommandButton btnCancelar = new CommandButton();
    private StreamedContent fileDownload;

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

//    public List<Pessoa> getPessoas() {
//        return pessoas;
//    }
//
//    public void setPessoas(List<Pessoa> pessoas) {
//        this.pessoas = pessoas;
//    }


    public LazyDataModel<Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(LazyDataModel<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

    public CommandButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public void setBtnRegistrar(CommandButton btnRegistrar) {
        this.btnRegistrar = btnRegistrar;
    }

    public CommandButton getBtnLimpar() {
        return btnLimpar;
    }

    public void setBtnLimpar(CommandButton btnLimpar) {
        this.btnLimpar = btnLimpar;
    }

    public CommandButton getBtnEditar() {
        return btnEditar;
    }

    public void setBtnEditar(CommandButton btnEditar) {
        this.btnEditar = btnEditar;
    }

    public CommandButton getBtnCancelar() {
        return btnCancelar;
    }

    public void setBtnCancelar(CommandButton btnCancelar) {
        this.btnCancelar = btnCancelar;
    }

    public CartaoCredito getCartaoCredito() {
        return cartaoCredito;
    }

    public void setCartaoCredito(CartaoCredito cartaoCredito) {
        this.cartaoCredito = cartaoCredito;
    }

    public List<Pessoa> getFilterPessoa() {
        return filterPessoa;
    }

    public void setFilterPessoa(List<Pessoa> filterPessoa) {
        this.filterPessoa = filterPessoa;
    }

    public StreamedContent getFileDownload() {
        byte[] bytes = Base64.getDecoder().decode(pessoa.getPerfilImagem64());
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        return  fileDownload = DefaultStreamedContent.builder()
                .name(pessoa.getPerfilImagemNome())
                .contentType(pessoa.getPerfilImagemType())
                .stream(() -> byteArrayInputStream).build();

    }

    @SuppressWarnings(value = "unchecked")
    @PostConstruct
    public void init() {
        pessoas = new LazyDataModel<Pessoa>() {
            @Override
            public List<Pessoa> load(int i, int i1, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
                setRowCount(lazyDataTableModelPessoa.getTotalRegistros());
                return lazyDataTableModelPessoa.listar(i,i1, map, map1);
            }
        };
//        pessoas = genericDao.getEntityList(Pessoa.class);
        Pessoa pessoaCC = (Pessoa) PrimeFacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("pessoaCC");
        if (pessoaCC != null) {
            pessoa = pessoaCC;
            PrimeFacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("pessoaCC");
        }
    }

    @SuppressWarnings(value = "unchecked")
    public String registrar() {
        try {
            if (pessoa.isCepValid()) {
                genericDao.insertEntity(pessoa);
                GlobalMessage.sendMessage(null, FacesMessage.SEVERITY_INFO, "Usuario cadastrado com sucesso");
//                recarregarListaPessoas();
                pessoa = new Pessoa();
            } else {
                GlobalMessage.sendMessage(null, FacesMessage.SEVERITY_ERROR, "Cep Invalido");
            }
        } catch (Exception e) {
            e.printStackTrace();
            GlobalMessage.sendMessage(null, FacesMessage.SEVERITY_ERROR, "Erro ao cadastrar usuario");
        }
        return "";
    }

    @SuppressWarnings(value = "unchecked")
    public String editar() {
        try {
            if (pessoa.isCepValid()) {
                genericDao.updateEntity(pessoa);
                recarregarListaPessoas();
                pessoa = new Pessoa();
                mostrarBtnRegistro();
                GlobalMessage.sendMessage(null, FacesMessage.SEVERITY_INFO, "Usuario atualizado com sucesso");
            } else {
                GlobalMessage.sendMessage(null, FacesMessage.SEVERITY_ERROR, "Cep Invalido");
            }
        } catch (Exception e) {
            e.printStackTrace();
            GlobalMessage.sendMessage(null, FacesMessage.SEVERITY_ERROR, "Erro ao atualizar usuario");
        }
        return "";
    }

    @SuppressWarnings(value = "unchecked")
    public String deletarPessoa() {
        try {
            iDaoPessoa.deletarPessoaEmCascata(pessoa);
            removerUsuarioEscolhido();
//            recarregarListaPessoas();
            GlobalMessage.sendMessage(null, FacesMessage.SEVERITY_INFO, "Pessoa deletada com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getCause().getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                GlobalMessage.sendMessage(null, FacesMessage.SEVERITY_ERROR, "Usuario tem telefones em seu registro, favor apagar telefones primeiro");
            } else {
                GlobalMessage.sendMessage(null, FacesMessage.SEVERITY_ERROR, "Erro ao deletar pessoa, tente novamente mais tarde ou contate o suporte");
            }
        }
        pessoa = new Pessoa();
        return "";
    }

    @SuppressWarnings(value = "unchecked")
    public void recarregarListaPessoas() {
//        pessoas = genericDao.getEntityList(Pessoa.class);
    }

    public void cancelar() {
        pessoa = new Pessoa();
        mostrarBtnRegistro();
    }

    public void esconderBtnRegistro() {
        btnRegistrar.setRendered(false);
        btnLimpar.setRendered(false);
        btnEditar.setRendered(true);
        btnCancelar.setRendered(true);
    }

    public void mostrarBtnRegistro() {
        btnEditar.setRendered(false);
        btnCancelar.setRendered(false);
        btnRegistrar.setRendered(true);
        btnLimpar.setRendered(true);
    }

    public String irPaginaTelefone() {
        PrimeFacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("pessoaEscolhida", pessoa);
        return "telefone.xhtml?faces-redirect=true&i=2";
    }

    public void removerUsuarioEscolhido() {
        pessoaEscolhida = (Pessoa) PrimeFacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("pessoaEscolhida");
        if (pessoaEscolhida != null && pessoa.getId().equals(pessoaEscolhida.getId())) {
            PrimeFacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("pessoaEscolhida");
        }
    }

    public void pesquisarCep() throws Exception {
        iServiceCall.getService(pessoa, "formRegister:cep");
    }

    public void modalCartaoCredito() {
        PrimeFacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("pessoaCC", pessoa);
        Map<String, Object> map = new HashMap<>();
        map.put("resizable", false);
        map.put("draggable", false);
        map.put("modal", true);
        PrimeFaces.current().dialog().openDynamic("cartaoCredito", map, null);
    }

    @SuppressWarnings(value = "unchecked")
    public void registrarCartao() {
        try {
            cartaoCredito.setPessoa(pessoa);
            genericDao.insertEntity(cartaoCredito);
            pessoa.getCartoesCredito().add(cartaoCredito);
            cartaoCredito = new CartaoCredito();
            GlobalMessage.sendMessage(null, FacesMessage.SEVERITY_INFO, "Cartao cadastrado com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
            GlobalMessage.sendMessage(null, FacesMessage.SEVERITY_INFO, "Erro ao cadastrar cartao");
        }
    }

    public void renovarPessoaFechandoDialog() {
        pessoa = new Pessoa();
    }

    @SuppressWarnings(value = "unchecked")
    public void deletarCartao() {
        try {
            genericDao.deleteByID(CartaoCredito.class, cartaoCredito.getId());
            pessoa.getCartoesCredito().remove(cartaoCredito);
            cartaoCredito = new CartaoCredito();
            GlobalMessage.sendMessage(null, FacesMessage.SEVERITY_INFO, "Cartao removido com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
            GlobalMessage.sendMessage(null, FacesMessage.SEVERITY_INFO, "Erro ao excluir cartao");
        }
    }

    public void fecharDialogo() {
        PrimeFaces.current().dialog().closeDynamic(null);
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {

        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();

        if (LangUtils.isValueBlank(filterText)) {
            return true;
        }

        int filterInt = getIntegerFilter(filterText);

        Pessoa p = (Pessoa) value;

        return p.getNome().toLowerCase().contains(filterText)
                || p.getEmail().toLowerCase().contains(filterText)
                || p.getIdade() == filterInt;
    }

    private int getIntegerFilter(String filterText) {
        try {
            return Integer.parseInt(filterText);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void upload(FileUploadEvent imagem) throws IOException {
        pessoa.setPerfilImagemNome(imagem.getFile().getFileName());
        pessoa.setPerfilImagemType(imagem.getFile().getContentType());
        pessoa.setPerfilImagem64(Base64.getEncoder().encodeToString(imagem.getFile().getContent()));

        String miniatura = new BuildMiniatureImage().createMiniatureImage(imagem.getFile().getInputStream(), imagem.getFile().getContentType());
        pessoa.setPerfilImagemMiniatura64(miniatura);
    }

}
