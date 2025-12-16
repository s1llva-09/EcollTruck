
package com.example.gestaodeprodutos.model;

public class PontoColeta {

    public enum Status {
        CONCLUIDO,
        ATIVO,
        PENDENTE
    }

    private String nome;
    private String endereco;
    private String horario;
    private int lixeiras;
    private int pesoEstimadoKg;
    private Status status;

    public PontoColeta(String nome, String endereco, String horario, int lixeiras, int pesoEstimadoKg, Status status) {
        this.nome = nome;
        this.endereco = endereco;
        this.horario = horario;
        this.lixeiras = lixeiras;
        this.pesoEstimadoKg = pesoEstimadoKg;
        this.status = status;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getHorario() {
        return horario;
    }

    public int getLixeiras() {
        return lixeiras;
    }

    public int getPesoEstimadoKg() {
        return pesoEstimadoKg;
    }

    public Status getStatus() {
        return status;
    }

    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public void setLixeiras(int lixeiras) {
        this.lixeiras = lixeiras;
    }

    public void setPesoEstimadoKg(int pesoEstimadoKg) {
        this.pesoEstimadoKg = pesoEstimadoKg;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
