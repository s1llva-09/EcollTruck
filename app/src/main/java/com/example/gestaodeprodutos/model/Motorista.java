package com.example.gestaodeprodutos.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Motorista implements Serializable {

    @SerializedName("id_motorista")
    private Integer id;

    @SerializedName("nome_completo")
    private String nomeCompleto;

    @SerializedName("cnh")
    private String cnh;

    @SerializedName("categoria_cnh")
    private String categoriaCnh;

    @SerializedName("telefone")
    private String telefone;

    @SerializedName("status")
    private String status;

    @SerializedName("id_cliente")
    private Integer idCliente;

    // Getters
    public Integer getId() { return id; }
    public String getNomeCompleto() { return nomeCompleto; }
    public Integer getIdCliente() { return idCliente; }

    // Setters
    public void setId(Integer id) { this.id = id; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }
    public void setCnh(String cnh) { this.cnh = cnh; }
    public void setCategoriaCnh(String categoriaCnh) { this.categoriaCnh = categoriaCnh; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setStatus(String status) { this.status = status; }
    public void setIdCliente(Integer idCliente) { this.idCliente = idCliente; }

    // Sobrescrevendo o método toString para exibir o nome completo no dropdown
    @Override
    public String toString() {
        return nomeCompleto;
    }
}
