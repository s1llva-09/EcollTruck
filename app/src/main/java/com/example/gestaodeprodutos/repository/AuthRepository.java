package com.example.gestaodeprodutos.repository;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;

import com.example.gestaodeprodutos.model.AuthResponse;
import com.example.gestaodeprodutos.network.ApiAuthService;
import com.example.gestaodeprodutos.network.RetrofitClient;
import com.example.gestaodeprodutos.view.UsuarioRegistro;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {

    private ApiAuthService api;
    private static final String TAG = "AuthRepository";

    private final String API_KEY = "sb_publishable_eN4SSA3iUkrz-EgXNdJqpQ_Fdh8Dn20";
    private final String CONTENT = "application/json";

    public AuthRepository() {
        api = RetrofitClient.getRetrofitInstance().create(ApiAuthService.class);
    }

    public void login(String email, String senha, MutableLiveData<AuthResponse> result, MutableLiveData<String> error) {

        Map<String, String> body = new HashMap<>();
        body.put("email", email);
        body.put("password", senha);

        api.login(API_KEY, CONTENT, body).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful()) {
                    result.setValue(response.body());
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e(TAG, "Login failed: " + errorBody);
                        if (errorBody.contains("Invalid login credentials")) {
                            error.setValue("E-mail ou senha inválidos.");
                        } else {
                            error.setValue("Erro ao fazer login. Verifique os dados.");
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Error parsing login error body", e);
                        error.setValue("Ocorreu um erro inesperado.");
                    }
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Log.e(TAG, "Login network failure", t);
                error.setValue("Falha na comunicação com o servidor.");
            }
        });
    }

    public void registrarUsuario(String name, String email, String password, String cpf, String phone, String birthDate, 
                                 MutableLiveData<AuthResponse> result, MutableLiveData<String> error) {

        UsuarioRegistro body = new UsuarioRegistro(name, email, password, cpf, phone, birthDate);

        api.registrar(API_KEY, body).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful()) {
                    result.setValue(response.body());
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e(TAG, "Registration failed: " + errorBody);
                        if (errorBody.contains("duplicate key value")) {
                            error.setValue("Este e-mail ou CPF já está em uso.");
                        } else if (errorBody.contains("Password should be at least 6 characters")) {
                            error.setValue("A senha deve ter no mínimo 6 caracteres.");
                        } else {
                            error.setValue("Erro ao registrar. Verifique os dados.");
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Error parsing registration error body", e);
                        error.setValue("Ocorreu um erro inesperado.");
                    }
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Log.e(TAG, "Registration network failure", t);
                error.setValue("Falha na comunicação com o servidor.");
            }
        });
    }
}
