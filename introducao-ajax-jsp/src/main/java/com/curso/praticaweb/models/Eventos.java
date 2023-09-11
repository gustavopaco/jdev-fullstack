package com.curso.praticaweb.models;

public class Eventos {
    private Long id_evento;
    private String title;
    private String datastart;
    private String dataend;
    private String url;
    private int groupId;

    public Long getId_evento() {
        return id_evento;
    }

    public void setId_evento(Long id_evento) {
        this.id_evento = id_evento;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDatastart() {
        return datastart;
    }

    public void setDatastart(String datastart) {
        this.datastart = datastart;
    }

    public String getDataend() {
        return dataend;
    }

    public void setDataend(String dataend) {
        this.dataend = dataend;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
