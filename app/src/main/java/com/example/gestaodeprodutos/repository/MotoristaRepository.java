package com.example.gestaodeprodutos.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.gestaodeprodutos.model.Motorista;
import com.example.gestaodeprodutos.network.ApiClient;
import com.example.gestaodeprodutos.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MotoristaRepository {

    private final ApiService apiService;

    public MotoristaRepository() {
        this.apiService = ApiClient.getApiService();
    }

    public void fetchMotoristas(String token, MutableLiveData<List<Motorista>> motoristas, MutableLiveData<String> error) {
        String authHeader = "Bearer " + token;
        apiService.listarMotoristas(authHeader).enqueue(new Callback<List<Motorista>>() {
            @Override
            public void onResponse(Call<List<Motorista>> call, Response<List<Motorista>> response) {
                if (response.isSuccessful()) {
                    motoristas.setValue(response.body());
                } else {
                    error.setValue("Erro ao buscar motoristas. Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Motorista>> call, Throwable t) {
                error.setValue("Falha na comunicação: " + t.getMessage());
            }
        });
    }

    public void searchMotoristas(String token, String query, MutableLiveData<List<Motorista>> motoristas, MutableLiveData<String> error) {
        String authHeader = "Bearer " + token;
        apiService.buscarMotoristaPorNome("nome.ilike.%" + query + "%", authHeader).enqueue(new Callback<List<Motorista>>() {
            @Override
            public void onResponse(Call<List<Motorista>> call, Response<List<Motorista>> response) {
                if (response.isSuccessful()) {
                    motoristas.setValue(response.body());
                } else {
                    error.setValue("Erro ao buscar motoristas. Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Motorista>> call, Throwable t) {
                error.setValue("Falha na comunicação: " + t.getMessage());
            }
        });
    }

    public void saveMotorista(String token, Motorista motorista, MutableLiveData<Boolean> success, MutableLiveData<String> error) {
        String authHeader = "Bearer " + token;
        apiService.inserirMotorista(authHeader, motorista).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    success.setValue(true);
                } else {
                    error.setValue("Erro ao salvar o motorista. Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                error.setValue("Falha na comunicação: " + t.getMessage());
            }
        });
    }
}
