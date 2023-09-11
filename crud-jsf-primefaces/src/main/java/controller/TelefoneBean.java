package controller;

import dao.GenericDao;
import model.Pessoa;
import model.TelefonePessoa;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.context.PrimeFacesContext;
import repository.IDaoTelefone;
import util.GlobalMessage;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionListener;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@Named
public class TelefoneBean implements Serializable {

    @Inject
    private GenericDao genericDao;

    @Inject
    private IDaoTelefone iDaoTelefone;

    private Pessoa pessoa;
    private List<TelefonePessoa> telefonePessoas = new ArrayList<>();
    private TelefonePessoa telefonePessoa = new TelefonePessoa();

    private CommandButton btnCadastro = new CommandButton();
    private CommandButton btnEditar = new CommandButton();
    private CommandButton btnLimpar = new CommandButton();
    private CommandButton btnCancelar = new CommandButton();

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public List<TelefonePessoa> getTelefonePessoas() {
        return telefonePessoas;
    }

    public void setTelefonePessoas(List<TelefonePessoa> telefonePessoas) {
        this.telefonePessoas = telefonePessoas;
    }

    public TelefonePessoa getTelefonePessoa() {
        return telefonePessoa;
    }

    public void setTelefonePessoa(TelefonePessoa telefonePessoa) {
        this.telefonePessoa = telefonePessoa;
    }

    public CommandButton getBtnCadastro() {
        return btnCadastro;
    }

    public void setBtnCadastro(CommandButton btnCadastro) {
        this.btnCadastro = btnCadastro;
    }

    public CommandButton getBtnEditar() {
        return btnEditar;
    }

    public void setBtnEditar(CommandButton btnEditar) {
        this.btnEditar = btnEditar;
    }

    public CommandButton getBtnLimpar() {
        return btnLimpar;
    }

    public void setBtnLimpar(CommandButton btnLimpar) {
        this.btnLimpar = btnLimpar;
    }

    public CommandButton getBtnCancelar() {
        return btnCancelar;
    }

    public void setBtnCancelar(CommandButton btnCancelar) {
        this.btnCancelar = btnCancelar;
    }

    @PostConstruct
    public void init() {
        pessoa = (Pessoa) PrimeFacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("pessoaEscolhida");
        telefonePessoas = pessoa.getTelefonePessoas();
    }

    @SuppressWarnings(value = "unchecked")
    public String registroTelefone() {
        try {
            telefonePessoa.setPessoa(pessoa);
            genericDao.insertEntity(telefonePessoa);
            recarregarTelefoneList();
            telefonePessoa = new TelefonePessoa();
            GlobalMessage.sendMessage(null, FacesMessage.SEVERITY_INFO,"Telefone cadastrado com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
            GlobalMessage.sendMessage(null, FacesMessage.SEVERITY_INFO,"Erro ao cadastrar telefone.");
        }
        return  "";
    }

    @SuppressWarnings(value = "unchecked")
    public String updateTelefone() {
        try {
            genericDao.updateEntity(telefonePessoa);
            mostrarBtnCadastro();
            recarregarTelefoneList();
            telefonePessoa = new TelefonePessoa();
            GlobalMessage.sendMessage(null,FacesMessage.SEVERITY_INFO,"Numero atualizado com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
            GlobalMessage.sendMessage(null,FacesMessage.SEVERITY_ERROR,"Erro ao alterar numero.");
        }
        return "";
    }

    @SuppressWarnings(value = "unchecked")
    public String deleteTelefone() {
        try {
            genericDao.deleteByID(TelefonePessoa.class, telefonePessoa.getId());
            recarregarTelefoneList();
            mostrarBtnCadastro();
            telefonePessoa = new TelefonePessoa();
            GlobalMessage.sendMessage(null,FacesMessage.SEVERITY_INFO,"Telefone deletado com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
            GlobalMessage.sendMessage(null,FacesMessage.SEVERITY_ERROR,"Erro ao deletar telefone.");
        }
        return "";
    }

    public void recarregarTelefoneList() {
        telefonePessoas = iDaoTelefone.getTelefoneListByUserID(pessoa.getId());
    }

    public void esconderBtnCadastro() {
        btnCadastro.setRendered(false);
        btnLimpar.setRendered(false);
        btnEditar.setRendered(true);
        btnCancelar.setRendered(true);
    }

    public void mostrarBtnCadastro() {
        btnEditar.setRendered(false);
        btnCancelar.setRendered(false);
        btnCadastro.setRendered(true);
        btnLimpar.setRendered(true);
    }

    public void cancelar() {
        telefonePessoa = new TelefonePessoa();
        mostrarBtnCadastro();
    }
}
