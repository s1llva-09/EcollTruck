package com.example.gestaodeprodutos.network;

import com.example.gestaodeprodutos.model.Caminhao;
import com.example.gestaodeprodutos.model.Cliente;
import com.example.gestaodeprodutos.model.Motorista;
import com.example.gestaodeprodutos.model.Rota;
import com.example.gestaodeprodutos.model.Usuario;

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

    // Rotas
    @Headers({
            "Content-Type: application/json",
            "Prefer: return=representation"
    })
    @POST("rest/v1/tb_rota")
    Call<Void> criarRota(
            @Header("Authorization") String auth,
            @Body Rota rota
    );

    // Caminhoes
    @Headers({
            "Accept: application/json",
            "Prefer: return=representation"
    })
    @GET("rest/v1/tb_caminhao?select=*,tb_cliente(*,tb_motorista(*))")
    Call<List<Caminhao>> listarCaminhoes(
            @Header("Authorization") String auth
    );

    @Headers({
            "Accept: application/json",
            "Prefer: return=representation"
    })
    @GET("rest/v1/tb_caminhao?select=*,tb_cliente(*,tb_motorista(*))")
    Call<List<Caminhao>> listarCaminhoesPorCliente(
            @Header("Authorization") String auth,
            @Query("id_cliente") String idCliente
    );

    @Headers({
            "Content-Type: application/json",
            "Prefer: return=representation"
    })
    @POST("rest/v1/tb_caminhao")
    Call<Void> inserirCaminhao(
            @Header("Authorization") String auth,
            @Body Caminhao caminhao
    );

    @Headers({
            "Content-Type: application/json",
            "Prefer: return=representation"
    })
    @PATCH("rest/v1/tb_caminhao")
    Call<Void> atualizarCaminhao(
            @Header("Authorization") String auth,
            @Query("id_caminhao") String id,
            @Body Caminhao caminhao
    );

    @Headers({
            "Content-Type: application/json",
            "Prefer: return=representation"
    })
    @DELETE("rest/v1/tb_caminhao")
    Call<Void> deletarCaminhao(
            @Header("Authorization") String auth,
            @Query("id_caminhao") String id
    );

    // Motoristas
    @Headers({
            "Accept: application/json"
    })
    @GET("rest/v1/tb_motorista?select=id_motorista,nome_completo,id_cliente&order=nome_completo.asc")
    Call<List<Motorista>> listarMotoristas(
            @Header("Authorization") String auth
    );

    @Headers({
        "Accept: application/json"
    })
    @GET("rest/v1/tb_motorista?select=id_motorista,nome_completo,id_cliente&order=nome_completo.asc")
    Call<List<Motorista>> buscarMotoristaPorNome(
            @Query("nome_completo") String nomeQuery,
            @Header("Authorization") String auth
    );


    @Headers({
            "Content-Type: application/json",
            "Prefer: return=representation"
    })
    @POST("rest/v1/tb_motorista")
    Call<Void> inserirMotorista(
            @Header("Authorization") String auth,
            @Body Motorista motorista
    );

    // Clientes
    @Headers({
            "Accept: application/json"
    })
    @GET("rest/v1/tb_cliente?select=id_cliente,nome_completo&order=nome_completo.asc")
    Call<List<Cliente>> listarClientes(
            @Header("Authorization") String auth
    );

    // Usuarios
    @Headers({
            "Accept: application/json"
    })
    @GET("rest/v1/tb_usuario?select=id_usuario,nome,id_cliente&order=nome.asc")
    Call<List<Usuario>> listarUsuarios(
            @Header("Authorization") String auth
    );
}
