package com.example.gestaodeprodutos.model;

public class Alerta {
    private String titulo;
    private String descricao;
    private String tipo;
    private String info;

    public Alerta(String titulo, String descricao, String tipo, String info) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.tipo = tipo;
        this.info = info;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public String getInfo() {
        return info;
    }
}
