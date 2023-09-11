package beans;

import dao.GenericDAO;
import model.Usuario;
import repository.IDaoUsuario;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@RequestScoped
@Named(value = "loginUserBean")
public class LoginUserBean implements Serializable {

    private Usuario usuario = new Usuario();
    private HtmlInputHidden urlEscolhida = new HtmlInputHidden();

    @Inject
    private IDaoUsuario iDaoUsuario;

    @Inject
    private GenericDAO genericDAO;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public HtmlInputHidden getUrlEscolhida() {
        return urlEscolhida;
    }

    public void setUrlEscolhida(HtmlInputHidden urlEscolhida) {
        this.urlEscolhida = urlEscolhida;
    }

    public String signIn() {
        try {
            usuario = iDaoUsuario.signIn(usuario.getEmail_usuario(), usuario.getPassword_usuario());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogado", usuario);
            if (urlEscolhida.getValue().toString().isEmpty()) {
                return "home.xhtml?faces-redirect=true";
            }else{
                return urlEscolhida.getValue().toString() + "?faces-redirect=true";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
