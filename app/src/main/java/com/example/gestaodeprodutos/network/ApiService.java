package com.example.gestaodeprodutos.network;

import com.example.gestaodeprodutos.model.Caminhao;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @Headers({
            "Accept: application/json",
            "Prefer: return=representation"
    })
    @GET("rest/v1/caminhoes?select=*&order=modelo.asc")
    Call<List<Caminhao>> listarCaminhoes(
            @Header("apikey") String apiKey,
            @Header("Authorization") String auth
    );

    @Headers({
            "Content-Type: application/json",
            "Prefer: return=representation"
    })
    @POST("rest/v1/caminhoes")
    Call<Void> inserirCaminhao(
            @Header("apikey") String apiKey,
            @Header("Authorization") String auth,
            @Body Caminhao caminhao
    );

    @Headers({
            "Content-Type: application/json",
            "Prefer: return=representation"
    })
    @PATCH("rest/v1/caminhoes")
    Call<Void> atualizarCaminhao(
            @Header("apikey") String apiKey,
            @Header("Authorization") String auth,
            @Query("id") String id,
            @Body Caminhao caminhao
    );

    @Headers({
            "Content-Type: application/json",
            "Prefer: return=representation"
    })
    @DELETE("rest/v1/caminhoes")
    Call<Void> deletarCaminhao(
            @Header("apikey") String apiKey,
            @Header("Authorization") String auth,
            @Query("id") String id
    );
}
