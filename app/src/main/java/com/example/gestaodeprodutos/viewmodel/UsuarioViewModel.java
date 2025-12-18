package com.example.gestaodeprodutos.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gestaodeprodutos.model.Usuario;
import com.example.gestaodeprodutos.repository.UsuarioRepository;

import java.util.List;

public class UsuarioViewModel extends ViewModel {

    private final UsuarioRepository repository;
    private final MutableLiveData<List<Usuario>> usuarios = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public UsuarioViewModel() {
        this.repository = new UsuarioRepository();
    }

    public LiveData<List<Usuario>> getUsuarios() {
        return usuarios;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void fetchUsuarios(String token) {
        repository.fetchUsuarios(token, usuarios, error);
    }
}
