package models;

import java.util.Objects;

public class Endereco {
    private Long id_endereco;
    private String end_cep;
    private String end_rua;
    private int end_numero;
    private String end_complemento;
    private String end_bairro;
    private String end_cidade;
    private String end_estado;
    private Long id_usuario;

    public Long getId_endereco() {
        return id_endereco;
    }

    public void setId_endereco(Long id_endereco) {
        this.id_endereco = id_endereco;
    }

    public String getEnd_cep() {
        return end_cep;
    }

    public void setEnd_cep(String end_cep) {
        this.end_cep = end_cep;
    }

    public String getEnd_rua() {
        return end_rua;
    }

    public void setEnd_rua(String end_rua) {
        this.end_rua = end_rua;
    }

    public int getEnd_numero() {
        return end_numero;
    }

    public void setEnd_numero(int end_numero) {
        this.end_numero = end_numero;
    }

    public String getEnd_complemento() {
        return end_complemento;
    }

    public void setEnd_complemento(String end_complemento) {
        this.end_complemento = end_complemento;
    }

    public String getEnd_bairro() {
        return end_bairro;
    }

    public void setEnd_bairro(String end_bairro) {
        this.end_bairro = end_bairro;
    }

    public String getEnd_cidade() {
        return end_cidade;
    }

    public void setEnd_cidade(String end_cidade) {
        this.end_cidade = end_cidade;
    }

    public String getEnd_estado() {
        return end_estado;
    }

    public void setEnd_estado(String end_estado) {
        this.end_estado = end_estado;
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
        if (!(o instanceof Endereco)) return false;
        Endereco endereco = (Endereco) o;
        return Objects.equals(id_endereco, endereco.id_endereco) && Objects.equals(end_cep, endereco.end_cep) && Objects.equals(end_rua, endereco.end_rua) && Objects.equals(end_numero, endereco.end_numero) && Objects.equals(end_complemento, endereco.end_complemento) && Objects.equals(end_bairro, endereco.end_bairro) && Objects.equals(end_cidade, endereco.end_cidade) && Objects.equals(end_estado, endereco.end_estado) && Objects.equals(id_usuario, endereco.id_usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_endereco, end_cep, end_rua, end_numero, end_complemento, end_bairro, end_cidade, end_estado, id_usuario);
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "id_endereco=" + id_endereco +
                ", end_cep='" + end_cep + '\'' +
                ", end_rua='" + end_rua + '\'' +
                ", end_numero=" + end_numero +
                ", end_complemento='" + end_complemento + '\'' +
                ", end_bairro='" + end_bairro + '\'' +
                ", end_cidade='" + end_cidade + '\'' +
                ", end_estado='" + end_estado + '\'' +
                ", id_usuario=" + id_usuario +
                '}';
    }
}
