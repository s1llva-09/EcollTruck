package com.example.gestaodeprodutos.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gestaodeprodutos.model.AuthResponse;
import com.example.gestaodeprodutos.repository.AuthRepository;

public class AuthViewModel extends ViewModel {

    private AuthRepository repository = new AuthRepository();

    // LiveData para o resultado do Login
    private MutableLiveData<AuthResponse> loginResult = new MutableLiveData<>();
    private MutableLiveData<String> loginError = new MutableLiveData<>();

    // LiveData para o resultado do Registro (Usuário e Empresa)
    private MutableLiveData<AuthResponse> registrationResult = new MutableLiveData<>();
    private MutableLiveData<String> registrationError = new MutableLiveData<>();

    // --- Métodos de Login ---
    public LiveData<AuthResponse> getLoginResult() {
        return loginResult;
    }

    public LiveData<String> getLoginError() {
        return loginError;
    }

    public void login(String email, String senha) {
        repository.login(email, senha, loginResult, loginError);
    }

    // --- Métodos de Registro ---
    public LiveData<AuthResponse> getRegistrationResult() {
        return registrationResult;
    }

    public LiveData<String> getRegistrationError() {
        return registrationError;
    }

    public void registrar(String name, String email, String password, String cpf, String phone, String birthDate) {
        repository.registrarUsuario(name, email, password, cpf, phone, birthDate, registrationResult, registrationError);
    }

    public void registrarEmpresa(String cnpj, String nomeResponsavel, String email, String phone, String cidade, String estado, String senha) {
        repository.registrarEmpresa(cnpj, nomeResponsavel, email, phone, cidade, estado, senha, registrationResult, registrationError);
    }
}
