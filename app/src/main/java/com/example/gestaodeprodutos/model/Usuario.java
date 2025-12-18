package com.example.gestaodeprodutos.model;

import com.google.gson.annotations.SerializedName;

public class Usuario {

    @SerializedName("id_usuario")
    private String id;

    @SerializedName("nome")
    private String nome;

    @SerializedName("email")
    private String email;

    @SerializedName("id_cliente")
    private Integer idCliente;

    @SerializedName("perfil")
    private String perfil;

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public String getPerfil() {
        return perfil;
    }

    @Override
    public String toString() {
        return nome;
    }
}
