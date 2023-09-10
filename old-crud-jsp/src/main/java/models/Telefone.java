package models;

import java.util.Objects;

public class Telefone {
    private Long id_telefone;
    private String tel_numero;
    private String tel_tipo;
    private Long id_usuario;

    public Long getId_telefone() {
        return id_telefone;
    }

    public void setId_telefone(Long id_telefone) {
        this.id_telefone = id_telefone;
    }

    public String getTel_numero() {
        return tel_numero;
    }

    public void setTel_numero(String tel_numero) {
        this.tel_numero = tel_numero;
    }

    public String getTel_tipo() {
        return tel_tipo;
    }

    public void setTel_tipo(String tel_tipo) {
        this.tel_tipo = tel_tipo;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Telefone telefone)) return false;
        return Objects.equals(id_telefone, telefone.id_telefone) && Objects.equals(tel_numero, telefone.tel_numero) && Objects.equals(tel_tipo, telefone.tel_tipo) && Objects.equals(id_usuario, telefone.id_usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_telefone, tel_numero, tel_tipo, id_usuario);
    }

    @Override
    public String toString() {
        return "Telefone{" +
                "id_telefone=" + id_telefone +
                ", tel_numero='" + tel_numero + '\'' +
                ", tel_tipo='" + tel_tipo + '\'' +
                ", id_usuario=" + id_usuario +
                '}';
    }
}
