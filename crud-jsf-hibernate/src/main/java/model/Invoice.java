package model;

import javax.persistence.*;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Invoice implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_invoice;

    @NotEmpty(message = "Invoice Number can't be empty")
    @NotNull(message = "Invoice Number can't be null")
    private String number_invoice;

    @Size(min = 5, max = 50, message = "Company name must have at least 5 characters")
    private String from_company_invoice;

    private String to_company_invoice;

    @ManyToOne
    private Usuario usuario;

    public Long getId_invoice() {
        return id_invoice;
    }

    public void setId_invoice(Long id_invoice) {
        this.id_invoice = id_invoice;
    }

    public String getNumber_invoice() {
        return number_invoice;
    }

    public void setNumber_invoice(String number_invoice) {
        this.number_invoice = number_invoice;
    }

    public String getFrom_company_invoice() {
        return from_company_invoice;
    }

    public void setFrom_company_invoice(String from_company_invoice) {
        this.from_company_invoice = from_company_invoice;
    }

    public String getTo_company_invoice() {
        return to_company_invoice;
    }

    public void setTo_company_invoice(String to_company_invoice) {
        this.to_company_invoice = to_company_invoice;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invoice)) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(id_invoice, invoice.id_invoice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_invoice);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id_invoice=" + id_invoice +
                ", number_invoice='" + number_invoice + '\'' +
                ", from_company_invoice='" + from_company_invoice + '\'' +
                ", to_company_invoice='" + to_company_invoice + '\'' +
                ", usuario=" + usuario +
                '}';
    }
}
