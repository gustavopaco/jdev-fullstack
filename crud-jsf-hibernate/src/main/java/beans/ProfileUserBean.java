package beans;

import dao.GenericDAO;
import model.Estados;
import model.Usuario;
import repository.IDaoUsuario;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@ViewScoped
@Named(value = "profileUserBean")
public class ProfileUserBean implements Serializable {

    private Usuario usuario = new Usuario();
    private List<SelectItem> selectItemsCidades = new ArrayList<>();
    private Part filePart;

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

    public Part getFilePart() {
        return filePart;
    }

    public void setFilePart(Part filePart) {
        this.filePart = filePart;
    }

    @PostConstruct
    public void getUsuarioEscolhido() {
        usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("userEscolhido");
        loadOnChangeEditCityState();
    }

    @SuppressWarnings(value = "unchecked")
    public String updateUsuario() {


        try {
            if (filePart != null) {
                /* Processa imagem */
                byte[] bytes = processFiles(filePart);
                /* Cria miniatura de imagem 200x200*/
                String miniatureIMG = createMiniatureImage(filePart);
                /* Recebendo tipo de arquivo ja formatado */
                String fileType = filePart.getContentType();

                usuario.setFileMainBase64(bytes);
                usuario.setFileIconBase64(miniatureIMG);
                usuario.setFileType(fileType);
            }

            genericDAO.updateEntity(usuario);
            return "home.xhtml?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            try {
                if (filePart != null) {
                    filePart.getInputStream().close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] processFiles(Part filePart) throws IOException {

        if (filePart != null) {
            return filePart.getInputStream().readAllBytes();
        }
        return null;
    }

    private String createMiniatureImage(Part filePart) throws IOException {

        /* Transforma em bufferImage chamando o metodo de ler Part file e retorna byte[]*/
        BufferedImage bufferedImage = ImageIO.read(filePart.getInputStream());
        /* Tipo da Imagem */
        int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();

        int fileheight = 200;
        int filewidth = 200;

        /* Criando miniatura */
        BufferedImage miniatureImage = new BufferedImage(filewidth, fileheight, type);
        Graphics2D graphics2D = miniatureImage.createGraphics();
        graphics2D.drawImage(bufferedImage, 0, 0, filewidth, fileheight, null);
        graphics2D.dispose();

        /* Escrevendo novamente a imagem em tamanho menor */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String extensao = filePart.getContentType().split("/")[1];
        ImageIO.write(miniatureImage, extensao, baos);

//        return "data:" + filePart.getContentType() + ";base64," + DatatypeConverter.printBase64Binary(baos.toByteArray()); **Qualquer um dos jeitos da certo**
        return "data:" + filePart.getContentType() + ";base64," + Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    public void findAddressZipCode() {
        try {
            usuario = iDaoUsuario.findAddress("editForm:zipcode", usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void preencherCidades(AjaxBehaviorEvent event) {

        Estados estado = (Estados) ((HtmlSelectOneMenu) event.getSource()).getValue();
        if (estado != null) {
            usuario.setEstado(estado);
            selectItemsCidades = iDaoUsuario.getCities(estado.getId());
        } else {
            selectItemsCidades = new ArrayList<>();
        }
//        String estadoID = (String) event.getComponent().getAttributes().get("submittedValue");
    }

    public void loadOnChangeEditCityState() {

        if (usuario.getCidade() != null) {

            usuario.setEstado(usuario.getCidade().getEstados());
            selectItemsCidades = iDaoUsuario.getCities(usuario.getEstado().getId());

        }
    }

    /* Metodo que converte um InputStream em array de bytes*/
    private byte[] getByteArray(InputStream inputStream) throws IOException {

        int len;
        int size = 1024;
        byte[] buffer = null;

        if (inputStream instanceof ByteArrayInputStream) {

            size = inputStream.available();
            buffer = new byte[size];
            len = inputStream.read(buffer, 0, size);
        } else {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            buffer = new byte[size];

            while ((len = inputStream.read(buffer, 0, size)) != -1) {

                bos.write(buffer, 0, len);
            }
            buffer = bos.toByteArray();
        }
        return buffer;
    }
}