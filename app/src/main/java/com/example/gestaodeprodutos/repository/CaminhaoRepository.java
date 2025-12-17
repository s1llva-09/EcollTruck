package com.example.gestaodeprodutos.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.gestaodeprodutos.model.Caminhao;
import com.example.gestaodeprodutos.network.ApiClient;
import com.example.gestaodeprodutos.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CaminhaoRepository {

    private final ApiService apiService;
    // A chave de API (anon) do Supabase. É seguro deixá-la aqui.
    private final String apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImNzcHdkcXpiYXhlcWh2YXZicmgiLCJyb2xlIjoiYW5vbiIsImlhdCI6MTcxNzA0MTEzNiwiZXhwIjoyMDMyNjE3MTM2fQ.KOj_iCg27f6nSfc-4r1yV3vG6p8pY3eJgH0N25s3nL4";

    public CaminhaoRepository() {
        this.apiService = ApiClient.getApiService();
    }

    public void saveCaminhao(String token, Caminhao caminhao, MutableLiveData<Boolean> success, MutableLiveData<String> error) {
        
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
            // Inserir novo caminhão
            apiService.inserirCaminhao(apiKey, "Bearer " + token, caminhao).enqueue(callback);
        } else {
            // Atualizar caminhão existente
            apiService.atualizarCaminhao(apiKey, "Bearer " + token, "eq." + caminhao.getId(), caminhao).enqueue(callback);
        }
    }
}
