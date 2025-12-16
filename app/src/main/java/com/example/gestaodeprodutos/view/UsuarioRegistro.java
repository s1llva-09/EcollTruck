package com.example.gestaodeprodutos.view;

import com.google.gson.annotations.SerializedName;

public class UsuarioRegistro {

    private String email;
    private String password;

    @SerializedName("data")
    private Data data;

    // Classe interna para o objeto 'data'
    private static class Data {
        private String name;
        private String cpf;
        private String phone;

        @SerializedName("birth_date")
        private String birthDate;

        public Data(String name, String cpf, String phone, String birthDate) {
            this.name = name;
            this.cpf = cpf;
            this.phone = phone;
            this.birthDate = birthDate;
        }
    }

    // Construtor principal que monta a estrutura aninhada
    public UsuarioRegistro(String name, String email, String password, String cpf, String phone, String birthDate) {
        this.email = email;
        this.password = password;
        // Cria o objeto aninhado data
        this.data = new Data(name, cpf, phone, birthDate);
    }
}
