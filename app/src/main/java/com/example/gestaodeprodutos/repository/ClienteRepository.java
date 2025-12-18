package com.example.gestaodeprodutos.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.gestaodeprodutos.model.Cliente;
import com.example.gestaodeprodutos.network.ApiClient;
import com.example.gestaodeprodutos.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClienteRepository {

    private final ApiService apiService;

    public ClienteRepository() {
        this.apiService = ApiClient.getApiService();
    }

    public void fetchClientes(String token, MutableLiveData<List<Cliente>> clientes, MutableLiveData<String> error) {
        String authHeader = "Bearer " + token;
        apiService.listarClientes(authHeader).enqueue(new Callback<List<Cliente>>() {
            @Override
            public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {
                if (response.isSuccessful()) {
                    clientes.setValue(response.body());
                } else {
                    error.setValue("Erro ao buscar clientes. Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Cliente>> call, Throwable t) {
                error.setValue("Falha na comunicação: " + t.getMessage());
            }
        });
    }
}
