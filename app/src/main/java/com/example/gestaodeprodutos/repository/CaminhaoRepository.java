package com.example.gestaodeprodutos.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.gestaodeprodutos.model.Caminhao;
import com.example.gestaodeprodutos.network.ApiClient;
import com.example.gestaodeprodutos.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CaminhaoRepository {

    private final ApiService apiService;

    public CaminhaoRepository() {
        this.apiService = ApiClient.getApiService();
    }

    public void saveCaminhao(String token, Caminhao caminhao, MutableLiveData<Boolean> success, MutableLiveData<String> error) {
        String authHeader = "Bearer " + token;

        Callback<Void> callback = new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    success.setValue(true);
                } else {
                    error.setValue("Erro ao salvar o caminhão. Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                error.setValue("Falha na comunicação: " + t.getMessage());
            }
        };

        if (caminhao.getId() == null) {
            apiService.inserirCaminhao(authHeader, caminhao).enqueue(callback);
        } else {
            apiService.atualizarCaminhao(authHeader, "eq." + caminhao.getId(), caminhao).enqueue(callback);
        }
    }

    public void deleteCaminhao(String token, int id, MutableLiveData<Boolean> success, MutableLiveData<String> error) {
        String authHeader = "Bearer " + token;
        apiService.deletarCaminhao(authHeader, "eq." + id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    success.setValue(true);
                } else {
                    error.setValue("Erro ao excluir o caminhão. Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                error.setValue("Falha na comunicação: " + t.getMessage());
            }
        });
    }

    public void fetchCaminhoes(String token, MutableLiveData<List<Caminhao>> caminhoes, MutableLiveData<String> error) {
        String authHeader = "Bearer " + token;
        apiService.listarCaminhoes(authHeader).enqueue(new Callback<List<Caminhao>>() {
            @Override
            public void onResponse(Call<List<Caminhao>> call, Response<List<Caminhao>> response) {
                if (response.isSuccessful()) {
                    caminhoes.setValue(response.body());
                } else {
                    error.setValue("Erro ao buscar caminhões. Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Caminhao>> call, Throwable t) {
                error.setValue("Falha na comunicação: " + t.getMessage());
            }
        });
    }
}
