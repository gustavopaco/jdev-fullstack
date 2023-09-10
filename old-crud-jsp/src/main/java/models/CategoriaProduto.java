package models;

import java.util.Objects;

public class CategoriaProduto {
    Long id_categoria;
    String nome_categoria;

    public Long getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Long id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getNome_categoria() {
        return nome_categoria;
    }

    public void setNome_categoria(String nome_categoria) {
        this.nome_categoria = nome_categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoriaProduto)) return false;
        CategoriaProduto that = (CategoriaProduto) o;
        return Objects.equals(id_categoria, that.id_categoria) && Objects.equals(nome_categoria, that.nome_categoria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_categoria, nome_categoria);
    }

    @Override
    public String toString() {
        return "CategoriaProduto{" +
                "id_categoria=" + id_categoria +
                ", nome_categoria='" + nome_categoria + '\'' +
                '}';
    }
}
