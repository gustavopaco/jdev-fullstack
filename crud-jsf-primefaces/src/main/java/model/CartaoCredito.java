package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class CartaoCredito implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String numero_cartao;

    private String validade;

    private String cvv;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Pessoa pessoa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero_cartao() {
        return numero_cartao;
    }

    public void setNumero_cartao(String numero_cartao) {
        this.numero_cartao = numero_cartao;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartaoCredito)) return false;
        CartaoCredito that = (CartaoCredito) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CartaoCredito{" +
                "id=" + id +
                ", numero_cartao='" + numero_cartao + '\'' +
                ", validade='" + validade + '\'' +
                ", cvv='" + cvv + '\'' +
                '}';
    }
}
