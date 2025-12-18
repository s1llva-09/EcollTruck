package com.example.gestaodeprodutos.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class Cliente implements Serializable {

    @SerializedName("id_cliente")
    private Integer id;

    @SerializedName("nome_completo")
    private String nomeCompleto;

    @SerializedName("tb_motorista") // Nome da tabela relacionada no Supabase
    private List<Motorista> motoristas;

    // Getters
    public Integer getId() {
        return id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public List<Motorista> getMotoristas() {
        return motoristas;
    }

    // Sobrescrevendo o método toString para exibir o nome no dropdown
    @Override
    public String toString() {
        return nomeCompleto;
    }
}
