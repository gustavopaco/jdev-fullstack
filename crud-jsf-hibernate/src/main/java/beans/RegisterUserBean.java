package beans;

import dao.GenericDAO;
import model.Estados;
import model.Usuario;
import repository.IDaoUsuario;
import util.DisplayMessages;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@Named(value = "registerUserBean")
public class RegisterUserBean implements Serializable {

    private Usuario usuario = new Usuario();
    private List<SelectItem> selectItemsCidades = new ArrayList<>();

    @Inject
    private GenericDAO genericDAO;

    @Inject
    private IDaoUsuario iDaoUsuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<SelectItem> getSelectItemsCidades() {
        return selectItemsCidades;
    }

    public void setSelectItemsCidades(List<SelectItem> selectItemsCidades) {
        this.selectItemsCidades = selectItemsCidades;
    }

    @SuppressWarnings(value = "unchecked")
    public String register() {
//        List<Framework> frameworkList = new ArrayList<>();
//            for (String fw : frameworks) {
//                Framework framework = (Framework) genericDAO.getEntityByID(new Framework(), fw.getId_framework());
//                frameworkList.add(framework);
//            }
//            usuario.setFrameworks(frameworks);
        try {

            if (!checkMailAlreadyTaken()) {
                genericDAO.insertEntity(usuario);

                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogado", getUsuario());
                return "home.xhtml?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().addMessage("registerForm:email", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Email", null));
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error while registering, contact support", null));
            return "";
        }
    }

    public Boolean checkMailAlreadyTaken() {
        if (usuario.getEmail_usuario() != null) {
            return iDaoUsuario.emailAlreadyTaken(usuario.getEmail_usuario());
        } else {
            return null;
        }
    }

    public void msgEmailAlreadyTaken() {
        String message;
        FacesMessage.Severity severity;
        if (checkMailAlreadyTaken()) {
            message = "Email already taken.";
            severity = FacesMessage.SEVERITY_ERROR;
        } else if (!checkMailAlreadyTaken()) {
            message = "Email is valid";
            severity = FacesMessage.SEVERITY_INFO;
        } else {
            message = "Invalid email.";
            severity = FacesMessage.SEVERITY_ERROR;
        }
        DisplayMessages.sendMessage("registerForm:email", severity, message);
        // Metodos de pegar compontente da tela por id na chamada por Ajax
//        UIComponent uiComponent = FacesContext.getCurrentInstance().getViewRoot().findComponent("registerForm:email");
//        FacesContext.getCurrentInstance().addMessage("registerForm:email", new FacesMessage(severity, message, null));
    }

    public void findAddressZipCode() {
        try {
            usuario = iDaoUsuario.findAddress("registerForm:zipcode", usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void preencherCidades(AjaxBehaviorEvent event) {

            Estados estado = (Estados) ((HtmlSelectOneMenu) event.getSource()).getValue();
            if (estado != null){
                usuario.setEstado(estado);
                selectItemsCidades = iDaoUsuario.getCities(estado.getId());
            }else{
                selectItemsCidades = new ArrayList<>();
            }
//        String estadoID = (String) event.getComponent().getAttributes().get("submittedValue");
    }


    public void registraLog() {
        System.out.println("Chamou Evento Registra log");
    }

    public void valorChange(ValueChangeEvent valueChangeEvent) {
        System.out.println("Chamou o valor Antigo" + valueChangeEvent.getOldValue());
        System.out.println("Chamou o valor Novo" + valueChangeEvent.getNewValue());
    }
}
