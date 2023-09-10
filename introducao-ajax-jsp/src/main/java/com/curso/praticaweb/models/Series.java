package com.curso.praticaweb.models;

public class Series {
    private Long id_series;
    private String nome_series;
    private String start_date;
    private String end_date;
    private Long projeto_fk;

    public Long getId_series() {
        return id_series;
    }

    public void setId_series(Long id_series) {
        this.id_series = id_series;
    }

    public String getNome_series() {
        return nome_series;
    }

    public void setNome_series(String nome_series) {
        this.nome_series = nome_series;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public Long getProjeto_fk() {
        return projeto_fk;
    }

    public void setProjeto_fk(Long projeto_fk) {
        this.projeto_fk = projeto_fk;
    }

    @Override
    public String toString() {
        return "Series{" +
                "id_series=" + id_series +
                ", nome_series='" + nome_series + '\'' +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", projeto_fk=" + projeto_fk +
                '}';
    }
}
