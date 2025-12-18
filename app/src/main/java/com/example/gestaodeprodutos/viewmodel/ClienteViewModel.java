package com.example.gestaodeprodutos.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gestaodeprodutos.model.Cliente;
import com.example.gestaodeprodutos.repository.ClienteRepository;

import java.util.List;

public class ClienteViewModel extends ViewModel {

    private final ClienteRepository repository;
    private final MutableLiveData<List<Cliente>> clientes = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public ClienteViewModel() {
        this.repository = new ClienteRepository();
    }

    public LiveData<List<Cliente>> getClientes() {
        return clientes;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void fetchClientes(String token) {
        repository.fetchClientes(token, clientes, error);
    }
}
