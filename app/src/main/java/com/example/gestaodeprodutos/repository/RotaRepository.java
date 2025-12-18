package com.example.gestaodeprodutos.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.gestaodeprodutos.model.Rota;
import com.example.gestaodeprodutos.network.ApiClient;
import com.example.gestaodeprodutos.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RotaRepository {

    private final ApiService apiService;
    private final MutableLiveData<Boolean> success = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public RotaRepository() {
        this.apiService = ApiClient.getApiService();
    }

    public void criarRota(Rota rota, String token) {
        String authHeader = "Bearer " + token;

        apiService.criarRota(authHeader, rota).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    success.postValue(true);
                } else {
                    error.postValue("Erro ao criar rota: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                error.postValue("Falha na comunicação: " + t.getMessage());
            }
        });
    }

    public LiveData<Boolean> getSuccess() {
        return success;
    }

    public LiveData<String> getError() {
        return error;
    }
}
