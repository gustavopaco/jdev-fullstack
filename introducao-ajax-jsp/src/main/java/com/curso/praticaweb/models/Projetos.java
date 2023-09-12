package com.curso.praticaweb.models;

import java.util.ArrayList;
import java.util.List;

public class Projetos {
    private Long id_projeto;
    private String nome_projeto;
    private List<Series> list = new ArrayList<>();

    public Long getId_projeto() {
        return id_projeto;
    }

    public void setId_projeto(Long id_projeto) {
        this.id_projeto = id_projeto;
    }

    public String getNome_projeto() {
        return nome_projeto;
    }

    public void setNome_projeto(String nome_projeto) {
        this.nome_projeto = nome_projeto;
    }

    public List<Series> getList() {
        return list;
    }

    public void setList(List<Series> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Projetos{" +
                "id_projeto=" + id_projeto +
                ", nome_projeto='" + nome_projeto + '\'' +
                ", list=" + list +
                '}';
    }
}
