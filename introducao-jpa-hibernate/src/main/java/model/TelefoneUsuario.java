package model;

import javax.persistence.*;

@Entity
public class TelefoneUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_telefone;

    @Column(nullable = false)
    private String numero_telefone;

    @Column(nullable = false)
    private String tipo_telefone;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario usuario;

    public Long getId_telefone() {
        return id_telefone;
    }

    public void setId_telefone(Long id_telefone) {
        this.id_telefone = id_telefone;
    }

    public String getNumero_telefone() {
        return numero_telefone;
    }

    public void setNumero_telefone(String numero_telefone) {
        this.numero_telefone = numero_telefone;
    }

    public String getTipo_telefone() {
        return tipo_telefone;
    }

    public void setTipo_telefone(String tipo_telefone) {
        this.tipo_telefone = tipo_telefone;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "TelefoneUsuario{" +
                "id_telefone=" + id_telefone +
                ", numero_telefone='" + numero_telefone + '\'' +
                ", tipo_telefone='" + tipo_telefone + '\'' + '}';
    }
}
