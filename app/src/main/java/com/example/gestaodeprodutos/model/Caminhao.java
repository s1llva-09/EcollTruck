package com.example.gestaodeprodutos.model;

import java.io.Serializable;

public class Caminhao implements Serializable {
    private Integer id;
    private String modelo;
    private String placa;
    private String status;
    private Double capacidade;
    private Integer ano;

    public Caminhao(String modelo, String placa, String status, Double capacidade, Integer ano) {
        this.modelo = modelo;
        this.placa = placa;
        this.status = status;
        this.capacidade = capacidade;
        this.ano = ano;
    }

    public Caminhao() {}

    public Integer getId() { return id; }
    public String getModelo() { return modelo; }
    public String getPlaca() { return placa; }
    public String getStatus() { return status; }
    public Double getCapacidade() { return capacidade; }
    public Integer getAno() { return ano; }

    public void setId(Integer id) { this.id = id; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setPlaca(String placa) { this.placa = placa; }
    public void setStatus(String status) { this.status = status; }
    public void setCapacidade(Double capacidade) { this.capacidade = capacidade; }
    public void setAno(Integer ano) { this.ano = ano; }
}
