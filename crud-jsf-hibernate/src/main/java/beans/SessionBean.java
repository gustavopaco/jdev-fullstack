package beans;

import dao.GenericDAO;
import model.Usuario;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@SessionScoped
@Named(value = "sessionBean")
public class SessionBean implements Serializable{

    @Inject
    private GenericDAO genericDAO;

    @PostConstruct
    public void init() {
    }

    public boolean permissaoUsuario(String statusAcesso) {

        Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");

        return usuario.getStatus().equals(statusAcesso);
    }

    public String logoutUsuario() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
        //Metodo de receber a sessao e invalidar
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        request.getSession().invalidate();
        return "index.xhtml?faces-redirect=true";
    }
}
