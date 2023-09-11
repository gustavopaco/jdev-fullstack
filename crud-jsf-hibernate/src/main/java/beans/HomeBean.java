package beans;

import dao.GenericDAO;
import model.Usuario;
import repository.IDaoUsuario;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestScoped
@Named(value = "homeBean")
public class HomeBean implements Serializable {

    private Usuario usuario;
    private List<Usuario> usuarios = new ArrayList<>();

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

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @PostConstruct
    public void init() {
        usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("user");
        usuarios = iDaoUsuario.getUsersOrderByName();
    }

    public String goEditPage(Usuario u) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("userEscolhido", u);
        return "profile.xhtml";
    }

    @SuppressWarnings(value = "unchecked")
    public String deleteUsuario(Usuario u) {

        try {
            genericDAO.deleteEntityByID(u, u.getId_usuario());
            return "home.xhtml?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void download() {

        Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String usuarioID = map.get("fileDownloadID");
        Usuario usuarioEscolhido = (Usuario) genericDAO.getEntityByID(new Usuario(), Long.parseLong(usuarioID));

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

        try {
            response.addHeader("Content-Disposition", "inline;filename=arquivo." + usuarioEscolhido.getFileType().split("/")[1]);
            response.setContentType(usuarioEscolhido.getFileType());
            response.setContentLength(usuarioEscolhido.getFileMainBase64().length);

            response.getOutputStream().write(usuarioEscolhido.getFileMainBase64());
            response.getOutputStream().flush();
            // * IMPORTANTE - NO JSF EH PRECISO FALAR QUE A RESPOSTA ESTA COMPLETA OU IRA GERAR UMA EXCEPTION NO OUTPUTSTREAM *
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
