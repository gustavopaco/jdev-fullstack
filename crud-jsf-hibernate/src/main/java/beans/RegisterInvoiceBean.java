package beans;

import dao.GenericDAO;
import model.Invoice;
import model.Usuario;
import repository.IDaoInvoice;
import util.DisplayMessages;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@Named(value = "registerInvoiceBean")
public class RegisterInvoiceBean implements Serializable {

    private Invoice invoice = new Invoice();
    private Usuario usuario;
    private List<Invoice> invoices = new ArrayList<>();
    private HtmlCommandButton registerButton = new HtmlCommandButton();
    private HtmlCommandButton cleanButton = new HtmlCommandButton();
    private HtmlCommandButton editButton = new HtmlCommandButton();
    private HtmlCommandButton cancelButton = new HtmlCommandButton();

    @Inject
    private GenericDAO genericDAO;

    @Inject
    private IDaoInvoice iDaoInvoice;

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public HtmlCommandButton getRegisterButton() {
        return registerButton;
    }

    public void setRegisterButton(HtmlCommandButton registerButton) {
        this.registerButton = registerButton;
    }

    public HtmlCommandButton getCleanButton() {
        return cleanButton;
    }

    public void setCleanButton(HtmlCommandButton cleanButton) {
        this.cleanButton = cleanButton;
    }

    public HtmlCommandButton getEditButton() {
        return editButton;
    }

    public void setEditButton(HtmlCommandButton editButton) {
        this.editButton = editButton;
    }

    public HtmlCommandButton getCancelButton() {
        return cancelButton;
    }

    public void setCancelButton(HtmlCommandButton cancelButton) {
        this.cancelButton = cancelButton;
    }

    @PostConstruct
    public void init() {
        try {
            usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");
            invoices = iDaoInvoice.getInvoicesByUserID(usuario.getId_usuario());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String registerInvoice() {
        try {
            invoice.setUsuario(usuario);

            genericDAO.insertEntity(invoice);
            // Posso retornar Registrado com sucesso nesse ponto
            DisplayMessages.sendMessage(null, FacesMessage.SEVERITY_INFO,"Invoice successfully registered.");
            reloadInvoces();
            invoice = new Invoice();
        } catch (Exception e) {
            e.printStackTrace();
            // Posso retornar erro ao tentar cadastrar nesse ponto
            DisplayMessages.sendMessage(null, FacesMessage.SEVERITY_ERROR, "Error: Invoice not registered.");
        }
        return "";
    }

    @SuppressWarnings(value = "unchecked")
    public String editInvoice() {

        try {
            invoice.setUsuario(usuario);

            genericDAO.updateEntity(invoice);
            // Posso retornar Editado com sucesso nesse ponto
            DisplayMessages.sendMessage(null, FacesMessage.SEVERITY_INFO, "Invoice successfully updated.");
            invoice = new Invoice(); // Limpando formulario
            showRegisterButton(); // Renderizando botoes de registro e clean
            reloadInvoces(); // Recarregando lista de faturas
        } catch (Exception e) {
            e.printStackTrace();
            // Posso retornar erro ao tentar editar nesse ponto
            DisplayMessages.sendMessage(null, FacesMessage.SEVERITY_ERROR, "Error: Something wrong happened when update Invoice, contact the system admin.");
        }
        return "";
    }

    @SuppressWarnings(value = "unchecked")
    public String deleteInvoice(Invoice iv) {

        try {
            genericDAO.deleteEntityByObject(iv);
            // Posso retornar Deletado com sucesso nesse ponto
            DisplayMessages.sendMessage(null, FacesMessage.SEVERITY_INFO, "Invoice successfully deleted");
            invoice = new Invoice();
            showRegisterButton();
            reloadInvoces();
        } catch (Exception e) {
            e.printStackTrace();
            // Posso retornar erro ao tentar deletar nesse ponto
            DisplayMessages.sendMessage(null, FacesMessage.SEVERITY_ERROR, "Error: Something wrong happened when deleting Invoice.");
        }
        return "";
    }

    public void newInvoice() {

        invoice = new Invoice();
        showRegisterButton();
    }

    public void reloadInvoces() {
        invoices = iDaoInvoice.getInvoicesByUserID(usuario.getId_usuario());
    }

    public void showEditButton() {
        registerButton.setRendered(false);
        cleanButton.setRendered(false);
        editButton.setRendered(true);
        cancelButton.setRendered(true);
    }

    public void showRegisterButton() {
        cancelButton.setRendered(false);
        editButton.setRendered(false);
        registerButton.setRendered(true);
        cleanButton.setRendered(true);
    }
}
