package com.example.gestaodeprodutos.model;

import java.io.Serializable;

public class PontoParada implements Serializable {

    public enum Status {
        COMPLETED,
        IN_PROGRESS,
        PENDING
    }

    private String local;
    private int lixeiras;
    private String horario;
    private Status status;

    public PontoParada(String local, int lixeiras, String horario, Status status) {
        this.local = local;
        this.lixeiras = lixeiras;
        this.horario = horario;
        this.status = status;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public int getLixeiras() {
        return lixeiras;
    }

    public void setLixeiras(int lixeiras) {
        this.lixeiras = lixeiras;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
