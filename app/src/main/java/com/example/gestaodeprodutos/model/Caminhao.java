package com.example.gestaodeprodutos.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Caminhao implements Serializable {

    @SerializedName("id_caminhao")
    private Integer id;

    @SerializedName("placa")
    private String placa;

    @SerializedName("status")
    private String status;

    @SerializedName("capacidade_maxima")
    private Double capacidade;

    @SerializedName("id_cliente")
    private Integer idCliente;

    @SerializedName("tb_cliente") // Este nome deve corresponder ao que a API retorna
    private Cliente cliente;

    public Caminhao(String placa, String status, Double capacidade, Integer idCliente) {
        this.placa = placa;
        this.status = status;
        this.capacidade = capacidade;
        this.idCliente = idCliente;
    }

    public Caminhao() {}

    public Integer getId() { return id; }
    public String getPlaca() { return placa; }
    public String getStatus() { return status; }
    public Double getCapacidade() { return capacidade; }
    public Integer getIdCliente() { return idCliente; }
    public Cliente getCliente() { return cliente; } // Getter para o cliente

    public void setId(Integer id) { this.id = id; }
    public void setPlaca(String placa) { this.placa = placa; }
    public void setStatus(String status) { this.status = status; }
    public void setCapacidade(Double capacidade) { this.capacidade = capacidade; }
    public void setIdCliente(Integer idCliente) { this.idCliente = idCliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; } // Setter para o cliente

    @Override
    public String toString() {
        return placa;
    }
}
