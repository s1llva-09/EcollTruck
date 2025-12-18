package com.example.gestaodeprodutos.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Rota implements Serializable {

    @SerializedName("id_caminhao")
    private String caminhaoId;
    @SerializedName("nome_motorista")
    private String motoristaNome;
    @SerializedName("nome_rota")
    private String rotaNome;
    private int progresso;
    @SerializedName("pontos_parada")
    private List<PontoParada> pontosParada;
    @SerializedName("id_cliente")
    private String id_cliente;

    public Rota(String caminhaoId, String motoristaNome, String rotaNome, int progresso, List<PontoParada> pontosParada, String id_cliente) {
        this.caminhaoId = caminhaoId;
        this.motoristaNome = motoristaNome;
        this.rotaNome = rotaNome;
        this.progresso = progresso;
        this.pontosParada = pontosParada;
        this.id_cliente = id_cliente;
    }

    public String getCaminhaoId() {
        return caminhaoId;
    }

    public void setCaminhaoId(String caminhaoId) {
        this.caminhaoId = caminhaoId;
    }

    public String getMotoristaNome() {
        return motoristaNome;
    }

    public void setMotoristaNome(String motoristaNome) {
        this.motoristaNome = motoristaNome;
    }

    public String getRotaNome() {
        return rotaNome;
    }

    public void setRotaNome(String rotaNome) {
        this.rotaNome = rotaNome;
    }

    public int getProgresso() {
        return progresso;
    }

    public void setProgresso(int progresso) {
        this.progresso = progresso;
    }

    public List<PontoParada> getPontosParada() {
        return pontosParada;
    }

    public void setPontosParada(List<PontoParada> pontosParada) {
        this.pontosParada = pontosParada;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }
}
