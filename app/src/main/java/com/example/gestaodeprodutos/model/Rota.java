package com.example.gestaodeprodutos.model;

import java.io.Serializable;
import java.util.List;

public class Rota implements Serializable {

    private String caminhaoId;
    private String motoristaNome;
    private String rotaNome;
    private int progresso;
    private List<PontoParada> pontosParada;

    public Rota(String caminhaoId, String motoristaNome, String rotaNome, int progresso, List<PontoParada> pontosParada) {
        this.caminhaoId = caminhaoId;
        this.motoristaNome = motoristaNome;
        this.rotaNome = rotaNome;
        this.progresso = progresso;
        this.pontosParada = pontosParada;
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
}
