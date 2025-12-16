package com.example.gestaodeprodutos.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.gestaodeprodutos.model.Caminhao;
import com.example.gestaodeprodutos.network.ApiService;
import com.example.gestaodeprodutos.network.RetrofitClient;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CaminhaoRepository {

    private ApiService apiService;
    private final String API_KEY = "sb_publishable_eN4SSA3iUkrz-EgXNdJqpQ_Fdh8Dn20";

    public CaminhaoRepository() {
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    }

    public void listarCaminhoes(MutableLiveData<List<Caminhao>> caminhoesLiveData, String token) {
        apiService.listarCaminhoes(API_KEY, token).enqueue(new Callback<List<Caminhao>>() {
            @Override
            public void onResponse(Call<List<Caminhao>> call, Response<List<Caminhao>> response) {
                Log.d("API_DEBUG", "Codigo: " + response.code());
                Log.d("API_DEBUG", "Body: " + new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    caminhoesLiveData.setValue(response.body());
                } else {
                    caminhoesLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Caminhao>> call, Throwable t) {
                caminhoesLiveData.setValue(null);
            }
        });
    }

    public MutableLiveData<Boolean> inserirCaminhao(Caminhao caminhao, String token) {
        MutableLiveData<Boolean> sucesso = new MutableLiveData<>();
        apiService.inserirCaminhao(API_KEY, token, caminhao).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                sucesso.setValue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                sucesso.setValue(false);
            }
        });
        return sucesso;
    }

    public MutableLiveData<Boolean> atualizarCaminhao(Caminhao caminhao, String token) {
        MutableLiveData<Boolean> sucesso = new MutableLiveData<>();
        String id = "eq." + caminhao.getId().toString();
        apiService.atualizarCaminhao(API_KEY, token, id, caminhao).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                sucesso.setValue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                sucesso.setValue(false);
            }
        });
        return sucesso;
    }

    public MutableLiveData<Boolean> deletarCaminhao(int id, String token) {
        MutableLiveData<Boolean> sucesso = new MutableLiveData<>();
        apiService.deletarCaminhao(API_KEY, token, "eq." + id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                sucesso.setValue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                sucesso.setValue(false);
            }
        });
        return sucesso;
    }
}
