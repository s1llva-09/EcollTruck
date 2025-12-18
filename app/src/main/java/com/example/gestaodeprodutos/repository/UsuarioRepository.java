package com.example.gestaodeprodutos.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.gestaodeprodutos.model.Usuario;
import com.example.gestaodeprodutos.network.ApiClient;
import com.example.gestaodeprodutos.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioRepository {

    private final ApiService apiService;

    public UsuarioRepository() {
        this.apiService = ApiClient.getApiService();
    }

    public void fetchUsuarios(String token, MutableLiveData<List<Usuario>> usuarios, MutableLiveData<String> error) {
        String authHeader = token;
        apiService.listarUsuarios(authHeader).enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful()) {
                    usuarios.setValue(response.body());
                } else {
                    error.setValue("Erro ao buscar usuários. Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                error.setValue("Falha na comunicação: " + t.getMessage());
            }
        });
    }
}
